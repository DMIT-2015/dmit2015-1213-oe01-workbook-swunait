package dmit2015.view;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import dmit2015.entity.Supplier;
import dmit2015.repository.SupplierRepository;

import java.io.Serializable;
import java.util.Map;

@Named("currentSupplierSelectOneMenu")
@ViewScoped
public class SupplierSelectOneMenu implements Serializable {
    @Inject
    private SupplierRepository _supplierRepository;
    @Getter
    private Map<String, Integer> supplierSelectItemMap;
    @Getter
    @Setter
    private Integer selectedSupplierId;

    @PostConstruct

    public void init() {
        supplierSelectItemMap = _supplierRepository.selectItemMap();
    }

    public Supplier getSelectedSupplierReference() {
        return _supplierRepository.getReference(selectedSupplierId);
    }
}