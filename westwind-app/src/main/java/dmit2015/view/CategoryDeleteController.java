package dmit2015.view;

import dmit2015.entity.Category;
import dmit2015.repository.CategoryRepository;

import lombok.Getter;
import lombok.Setter;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import jakarta.annotation.PostConstruct;
import jakarta.faces.annotation.ManagedProperty;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.util.Optional;

@Named("currentCategoryDeleteController")
@ViewScoped
public class CategoryDeleteController implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private CategoryRepository _categoryRepository;

    @Inject
    @ManagedProperty("#{param.editId}")
    @Getter
    @Setter
    private Integer editId;

    @Getter
    private Category existingCategory;

    @PostConstruct
    public void init() {
        Optional<Category> optionalCategory = _categoryRepository.findOptionalById(editId);
        if (optionalCategory.isPresent()) {
            existingCategory = optionalCategory.get();
        } else {
            Faces.redirect(Faces.getRequestURI().substring(0, Faces.getRequestURI().lastIndexOf("/")) + "/index.xhtml");
        }
    }

    public String onDelete() {
        String nextPage = "";
        try {
            _categoryRepository.deleteById(existingCategory.getId());
            Messages.addFlashGlobalInfo("Delete was successful.");
            nextPage = "index?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace();
            Messages.addGlobalError("Delete not successful.");
        }
        return nextPage;
    }
}