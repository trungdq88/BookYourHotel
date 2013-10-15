package jsf;

import jpa.entities.HotelProperties;
import jsf.util.JsfUtil;
import jsf.util.PaginationHelper;
import jpa.session.HotelPropertiesFacade;

import java.io.Serializable;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

@Named("hotelPropertiesController")
@SessionScoped
public class HotelPropertiesController implements Serializable {

    private HotelProperties current;
    private DataModel items = null;
    @EJB
    private jpa.session.HotelPropertiesFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public HotelPropertiesController() {
    }

    public HotelProperties getSelected() {
        if (current == null) {
            current = new HotelProperties();
            current.setHotelPropertiesPK(new jpa.entities.HotelPropertiesPK());
            selectedItemIndex = -1;
        }
        return current;
    }

    private HotelPropertiesFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {
                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (HotelProperties) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new HotelProperties();
        current.setHotelPropertiesPK(new jpa.entities.HotelPropertiesPK());
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            current.getHotelPropertiesPK().setPropertyID(current.getProperties().getPropertyID());
            current.getHotelPropertiesPK().setWebsiteID(current.getWebsite().getWebsiteID());
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/BundleCrawler").getString("HotelPropertiesCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/BundleCrawler").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (HotelProperties) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            current.getHotelPropertiesPK().setPropertyID(current.getProperties().getPropertyID());
            current.getHotelPropertiesPK().setWebsiteID(current.getWebsite().getWebsiteID());
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/BundleCrawler").getString("HotelPropertiesUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/BundleCrawler").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (HotelProperties) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/BundleCrawler").getString("HotelPropertiesDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/BundleCrawler").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public HotelProperties getHotelProperties(jpa.entities.HotelPropertiesPK id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = HotelProperties.class)
    public static class HotelPropertiesControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            HotelPropertiesController controller = (HotelPropertiesController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "hotelPropertiesController");
            return controller.getHotelProperties(getKey(value));
        }

        jpa.entities.HotelPropertiesPK getKey(String value) {
            jpa.entities.HotelPropertiesPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new jpa.entities.HotelPropertiesPK();
            key.setWebsiteID(Integer.parseInt(values[0]));
            key.setPropertyID(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(jpa.entities.HotelPropertiesPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getWebsiteID());
            sb.append(SEPARATOR);
            sb.append(value.getPropertyID());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof HotelProperties) {
                HotelProperties o = (HotelProperties) object;
                return getStringKey(o.getHotelPropertiesPK());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + HotelProperties.class.getName());
            }
        }
    }
}
