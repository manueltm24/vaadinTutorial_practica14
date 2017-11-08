package com.example.demo;


import com.example.demo.Entidades.Customer;
import com.example.demo.Repository.CustomerRepository;
import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
public class CustomerEditor extends VerticalLayout{

    private final CustomerRepository repository;

    private Customer customer;

    TextField firstName=new TextField("First Name");
    TextField LastName=new TextField("Last Name");
    TextField Birthday=new TextField("Birthday");
    TextField Email=new TextField("Email");

    Button save=new Button("Save", FontAwesome.SAVE);
    Button cancel=new Button("Cancel");
    Button delete=new Button("Delete", FontAwesome.TRASH_O);

    CssLayout actions=new CssLayout(save,cancel,delete);

    Binder<Customer> binder =new Binder<>(Customer.class);

    @Autowired
    public CustomerEditor(CustomerRepository repository){
        this.repository=repository;

        addComponents(firstName,LastName,Birthday,Email,actions);

        binder.bindInstanceFields(this);

        setSpacing(true);
        actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);


        save.addClickListener(e->repository.save(customer));
        delete.addClickListener(e->repository.delete(customer.getId()));
        cancel.addClickListener(e->editCustomer(customer));
        setVisible(false);
    }


    public  interface changeHandler{
        void onChange();
    }

    public final void editCustomer(Customer c){
        if(c==null){
            setVisible(false);
            return;
        }
        final boolean persisted= c.getId() != null;

        if(persisted){
            customer=repository.findOne(c.getId());
        }
        else {
            customer=c;
        }

        cancel.setVisible(persisted);
        binder.setBean(customer);
        setVisible(true);
        save.focus();
        firstName.selectAll();


    }

    public void setChangedHandler(changeHandler handler){
        save.addClickListener(e->handler.onChange()) ;
        delete.addClickListener(e->handler.onChange()) ;
    }

}
