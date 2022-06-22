package dmit2015.view;

import dmit2015.entity.Category;
import dmit2015.entity.Product;
import dmit2015.repository.ProductRepository;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import org.omnifaces.util.Messages;

import java.io.Serializable;
import java.util.List;

@Named("currentProductsByCategoryController")
@SessionScoped
public class ProductsByCategoryController implements Serializable {

    @Inject
    private CategorySelectOneMenu _currentCategorySelectOneMenu;

    @Inject
    private ProductRepository _productRepository;

    @Getter
    private List<Product> queryResultList;

    public void onSearchByCategory() {
        try {
            queryResultList = _productRepository.findByCategoryId(
                    _currentCategorySelectOneMenu.getSelectedCategoryId()
            );
            if (queryResultList.size() == 0) {
                Category selectedCategory = _currentCategorySelectOneMenu.getSelectedCategoryReference();
                String message = String.format("No results returned for %s", selectedCategory.getCategoryName());
                Messages.addGlobalInfo(message);
            } else {
                String message = String.format("Query returned %s result(s)", queryResultList.size());
                Messages.addGlobalInfo(message);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Messages.addGlobalWarn("Error performing search");
        }
    }

    public void onClear() {
        queryResultList = null;
        _currentCategorySelectOneMenu.setSelectedCategoryId(null);
    }
}
