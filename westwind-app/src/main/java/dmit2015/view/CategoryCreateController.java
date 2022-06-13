package dmit2015.view;

import dmit2015.entity.Category;
import dmit2015.repository.CategoryRepository;

import lombok.Getter;
import org.omnifaces.util.Messages;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named("currentCategoryCreateController")
@RequestScoped
public class CategoryCreateController {

    @Inject
    private CategoryRepository _categoryRepository;

    @Getter
    private Category newCategory = new Category();

    public String onCreateNew() {
        String nextPage = "";
        try {
            _categoryRepository.add(newCategory);
            Messages.addFlashGlobalInfo("Create was successful. {0}", newCategory.getId());
            nextPage = "index?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace();
            Messages.addGlobalError("Create was not successful. {0}", e.getMessage());
        }
        return nextPage;
    }

}