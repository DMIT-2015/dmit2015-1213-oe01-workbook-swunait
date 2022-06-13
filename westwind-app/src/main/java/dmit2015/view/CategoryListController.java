package dmit2015.view;

import dmit2015.entity.Category;
import dmit2015.repository.CategoryRepository;
import lombok.Getter;
import org.omnifaces.util.Messages;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named("currentCategoryListController")
@ViewScoped
public class CategoryListController implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private CategoryRepository _categoryRepository;

    @Getter
    private List<Category> categoryList;

    @PostConstruct  // After @Inject is complete
    public void init() {
        try {
            categoryList = _categoryRepository.findAll();
        } catch (Exception ex) {
            Messages.addGlobalError(ex.getMessage());
        }
    }
}