package dmit2015.view;

import dmit2015.entity.Product;
import dmit2015.repository.ProductRepository;

import lombok.Getter;
import org.omnifaces.util.Messages;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named("currentProductCreateController")
@RequestScoped
public class ProductCreateController {

    @Inject
    private ProductRepository _productRepository;

    @Getter
    private Product newProduct = new Product();

    @Inject
    private CategorySelectOneMenu categorySelectOneMenu;

    @Inject
    private SupplierSelectOneMenu supplierSelectOneMenu;

    public String onCreateNew() {
        String nextPage = "";
        try {
            newProduct.setDiscontinued(false);
            newProduct.setCategory(categorySelectOneMenu.getSelectedCategoryReference());
            newProduct.setSupplier(supplierSelectOneMenu.getSelectedSupplierReference());

            _productRepository.add(newProduct);
            Messages.addFlashGlobalInfo("Create was successful. {0}", newProduct.getId());
            nextPage = "index?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace();
            Messages.addGlobalError("Create was not successful. {0}", e.getMessage());
        }
        return nextPage;
    }

}