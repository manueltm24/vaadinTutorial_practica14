package com.example.demo;



import com.example.demo.Entidades.Customer;
import com.example.demo.Repository.CustomerRepository;
import com.vaadin.annotations.Theme;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;


@SpringUI
@Theme("valo")
public class MyUI extends UI{

    private final CustomerEditor editor;
    private final CustomerRepository repo;
    final Grid<Customer> grid;
    final TextField filter;

    private  final Button addNewButton;


    @Override
    protected void init(VaadinRequest request) {
        HorizontalLayout actions=new HorizontalLayout(filter,addNewButton);
        VerticalLayout mainLayout=new VerticalLayout(actions,grid,editor);

        setContent(mainLayout);

        grid.setHeight(300,Unit.PIXELS);
        grid.setWidth( 900,Unit.PIXELS);
        grid.setColumns("id","firstName","lastName","birthday","email");

        filter.setPlaceholder("Filtrar por apellido");

        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(e->listCustomers(e.getValue()));

        grid.asSingleSelect().addValueChangeListener(e->{
            editor.editCustomer(e.getValue());
        });

        addNewButton.addClickListener(e->editor.editCustomer(new Customer("","","","")));

        editor.setChangedHandler(()->{
            editor.setVisible(false);
            listCustomers(filter.getValue());
        });


        listCustomers(null);
    }

    @Autowired
    public MyUI(CustomerRepository repo,CustomerEditor editor)
    {
        this.editor=editor;
        this.repo=repo;
        this.grid=new Grid<>(Customer.class);
        this.filter=new TextField();
        this.addNewButton=new Button("New Customer", FontAwesome.PLUS);

    }


    private void listCustomers(String filterText){
        if(org.springframework.util.StringUtils.isEmpty(filterText)){
            grid.setItems(repo.findAll());
        }else {
            grid.setItems(repo.findByLastNameStartsWithIgnoreCase(filterText));
        }
    }
}
