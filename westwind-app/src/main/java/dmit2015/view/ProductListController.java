package dmit2015.view;

import dmit2015.entity.Product;
import dmit2015.repository.ProductRepository;
import lombok.Getter;
import org.omnifaces.util.Messages;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named("currentProductListController")
@ViewScoped
public class ProductListController implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private ProductRepository _productRepository;

    @Getter
    private List<Product> productList;

    @PostConstruct  // After @Inject is complete
    public void init() {
        try {
            productList = _productRepository.findAll();
        } catch (Exception ex) {
            Messages.addGlobalError(ex.getMessage());
        }
    }
}