package jsf;

import jpa.entities.RoomProperties;
import jsf.util.JsfUtil;
import jsf.util.PaginationHelper;
import jpa.session.RoomPropertiesFacade;

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

@Named("roomPropertiesController")
@SessionScoped
public class RoomPropertiesController implements Serializable {

    private RoomProperties current;
    private DataModel items = null;
    @EJB
    private jpa.session.RoomPropertiesFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public RoomPropertiesController() {
    }

    public RoomProperties getSelected() {
        if (current == null) {
            current = new RoomProperties();
            current.setRoomPropertiesPK(new jpa.entities.RoomPropertiesPK());
            selectedItemIndex = -1;
        }
        return current;
    }

    private RoomPropertiesFacade getFacade() {
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
        current = (RoomProperties) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new RoomProperties();
        current.setRoomPropertiesPK(new jpa.entities.RoomPropertiesPK());
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            current.getRoomPropertiesPK().setWebsiteID(current.getWebsite().getWebsiteID());
            current.getRoomPropertiesPK().setPropertyID(current.getProperties().getPropertyID());
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/BundleCrawler").getString("RoomPropertiesCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/BundleCrawler").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (RoomProperties) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            current.getRoomPropertiesPK().setWebsiteID(current.getWebsite().getWebsiteID());
            current.getRoomPropertiesPK().setPropertyID(current.getProperties().getPropertyID());
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/BundleCrawler").getString("RoomPropertiesUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/BundleCrawler").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (RoomProperties) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/BundleCrawler").getString("RoomPropertiesDeleted"));
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

    public RoomProperties getRoomProperties(jpa.entities.RoomPropertiesPK id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = RoomProperties.class)
    public static class RoomPropertiesControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RoomPropertiesController controller = (RoomPropertiesController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "roomPropertiesController");
            return controller.getRoomProperties(getKey(value));
        }

        jpa.entities.RoomPropertiesPK getKey(String value) {
            jpa.entities.RoomPropertiesPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new jpa.entities.RoomPropertiesPK();
            key.setWebsiteID(Integer.parseInt(values[0]));
            key.setPropertyID(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(jpa.entities.RoomPropertiesPK value) {
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
            if (object instanceof RoomProperties) {
                RoomProperties o = (RoomProperties) object;
                return getStringKey(o.getRoomPropertiesPK());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + RoomProperties.class.getName());
            }
        }
    }
}
