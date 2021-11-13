package com.szakdoga.szakdoga.app.service;

import com.szakdoga.szakdoga.app.dto.UpdateWebshopProduct;
import com.szakdoga.szakdoga.app.dto.WebshopProductDto;
import com.szakdoga.szakdoga.app.exception.NoEntityException;
import com.szakdoga.szakdoga.app.mapper.WebshopProductMapper;
import com.szakdoga.szakdoga.app.repository.ProductRepository;
import com.szakdoga.szakdoga.app.repository.WebshopProductRepository;
import com.szakdoga.szakdoga.app.repository.WebshopRepository;
import com.szakdoga.szakdoga.app.dto.Selectors;
import com.szakdoga.szakdoga.app.repository.entity.Component;
import com.szakdoga.szakdoga.app.repository.entity.Product;
import com.szakdoga.szakdoga.app.repository.entity.Webshop;
import com.szakdoga.szakdoga.app.repository.entity.WebshopProduct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.HttpStatusException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.scheduling.support.CronSequenceGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpServerErrorException;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebshopProductService {

    private final WebshopProductRepository webshopProductRepository;

    private final ProductRepository productRepository;

    private final WebshopRepository webshopRepository;

    private final WebshopProductMapper webshopProductMapper;

    private final WebScrapeService webScrapeService;

    public WebshopProduct saveWebshop(WebshopProductDto webshopProductDto){
        WebshopProduct webshopProduct = webshopProductMapper.webshopProductDtoToWebshopProduct(webshopProductDto);

        webshopProduct.setPrice(webScrapeService.getPrice(webshopProduct.getLink(),webshopProduct.getName()));
        return webshopProductRepository.save(webshopProduct);
    }

    public Optional<WebshopProduct> findById(Long id){
        return webshopProductRepository.findById(id);
    }

    public List<WebshopProduct> findAllByName(String name){
        return webshopProductRepository.findAllByName(name);
    }

    @Transactional
    @Scheduled /*(cron = "0/15 * * * * *")*/(cron = "0 0 0 * * *") //Quartz
    public void refreshWebshopProductPrice(){

            PageRequest firstPageRequest = PageRequest.of(0, 100, Sort.by("name"));
            var firstWebshopProductsPage = webshopProductRepository.findAll(firstPageRequest);
            updateProductPrice(firstWebshopProductsPage);
            int totalPages = firstWebshopProductsPage.getTotalPages();
            IntStream.rangeClosed(1, totalPages).forEach(value -> {
                PageRequest pageRequest = PageRequest.of(0, 100, Sort.by("name"));
                var webshopProductsPage = webshopProductRepository.findAll(pageRequest);
                updateProductPrice(webshopProductsPage);
            });
    }

    void updateProductPrice(Page<WebshopProduct> webshopProductsPage) {
        int newPrice;
        WebshopProduct webshopProduct = null;
        String name = null;
        boolean isUpdated = false;
        boolean isDeleted = false;
        for (var product : webshopProductsPage) {
            try {
                newPrice = webScrapeService.getPrice(product.getLink(), product.getName());

                webshopProduct = webshopProductRepository.findById(product.getId()).orElseThrow(() -> new NoEntityException("No Entity found"));
                if (newPrice != product.getPrice()) {
                    webshopProduct.setPrice(newPrice);
                    webshopProductRepository.save(webshopProduct);
                    isUpdated = true;
                }
            } catch (Exception e) {
                List<Component> components = new ArrayList<>(product.getComponents());
                    for (var component : components){
                        component.getWebshopProducts().remove(product);
                    }

                product.getComponents().clear();
                isDeleted = true;
                name = product.getName();
                webshopProductRepository.delete(product);
            }
            if (isUpdated) {
                log.info("frissítettem");
            }
            if (isDeleted){
                log.info("Deleted webshop product: " + name);
            }
        }
    }

    public void deleteWebshopProduct(Long webshopProductId){
        WebshopProduct webshopProduct = webshopProductRepository.findById(webshopProductId).orElseThrow(() -> new NoEntityException("Not found"));

        List<Component> components = new ArrayList<>(webshopProduct.getComponents());
        for (var component : components){
            component.getWebshopProducts().remove(webshopProduct);
        }
        webshopProduct.getComponents().clear();

        webshopProductRepository.delete(webshopProduct);
    }

    @Transactional
    public void addWebshopProductToWebshop(Long webshopId, Long webshopProductId){
        Webshop webshop = webshopRepository.findById(webshopId).orElseThrow();
        WebshopProduct webshopProduct = webshopProductRepository.findById(webshopProductId).orElseThrow();

        webshop.getWebshopProducts().add(webshopProduct);
        webshopProduct.setWebshop(webshop);
    }

/*    public List<WebshopProduct> webshopProductsFromProduct(Long productId){
        Product product = productRepository.findById(productId).orElseThrow(() -> new NoEntityException("Not found"));

        List<Component> components = product.getComponents();


      List<WebshopProduct> webshopProducts = new ArrayList<>();

        for (var component : components){
            for (WebshopProduct webshopProduct : component.getWebshopProducts()){
                webshopProducts.add(webshopProductRepository.findById(webshopProduct.getId()).orElseThrow());
            }

        }

        return webshopProducts;
    }*/

    public void updateWebshopProduct(Long webshopProductId, UpdateWebshopProduct updateWebshopProduct){
        WebshopProduct webshopProduct = webshopProductRepository.findById(webshopProductId).orElseThrow(() -> new NoEntityException("Not found"));

        if (updateWebshopProduct.getName() != null){
            webshopProduct.setName(updateWebshopProduct.getName());
        }

        if (updateWebshopProduct.getLink() != null){
            webshopProduct.setLink(updateWebshopProduct.getLink());
        }

        webshopProductRepository.save(webshopProduct);
    }
}
