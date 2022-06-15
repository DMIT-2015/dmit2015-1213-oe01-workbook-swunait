package dmit2015.view;

import dmit2015.entity.Category;
import dmit2015.repository.CategoryRepository;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import westwind.entity.Category;
import westwind.repository.CategoryRepository;

import java.io.Serializable;
import java.util.Map;

@Named("currentCategorySelectOneMenu")
@ViewScoped
public class CategorySelectOneMenu implements Serializable {
    @Inject
    private CategoryRepository _categoryRepository;
    @Getter
    private Map<String, Integer> categorySelectItemMap;
    @Getter
    @Setter
    private Integer selectedCategoryId;

    @PostConstruct
    public void init() {
        categorySelectItemMap = _categoryRepository.selectItemMap();
    }

    public Category getSelectedCategoryReference() {
        return _categoryRepository.getReference(selectedCategoryId);
    }

}