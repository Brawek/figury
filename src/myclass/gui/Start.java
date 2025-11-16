package myclass.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Start extends Frame {
   private Button btAdd;
   private Button btDel;
   private Button btInfo;
   private Choice chShape;
   private MenuBar menuBar;
   private Menu mBase; //!!!!!!!!!!!!!!!!
   private MenuItem itemNew;
   private MenuItem itemRead;
   private MenuItem itemSave;
   private MenuItem itemClose;
   private Panel lpanel;
   private Panel ppanel;
   private Label labLista;
   private List listFig;
   private Label labChoice;

    public Start(String title) throws HeadlessException {
        super(title);
        setSize(400,500);
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                dispose();
            }
        });
        menuBar = new MenuBar();
        mBase = new Menu("BAZA");
        itemNew = new MenuItem("Nowa");
        itemRead = new MenuItem("Wczytaj");
        itemSave = new MenuItem("Zapisz");
        itemClose = new MenuItem("Zakończ");
        mBase.add(itemNew);
        mBase.add(itemRead);
        mBase.add(itemSave);
        mBase.add(itemClose);
        menuBar.add(mBase);
        setMenuBar(menuBar);
        lpanel = new Panel();
        ppanel = new Panel();

        lpanel.setPreferredSize(new Dimension(200,400));
        lpanel.setBackground(Color.ORANGE);
        lpanel.setLayout(new BorderLayout());

        labLista = new Label("Lista figur");
        listFig = new List();
        lpanel.add(labLista,BorderLayout.NORTH);
        lpanel.add(listFig,BorderLayout.CENTER);
        add(lpanel);

        labChoice = new Label("Wybierz Figure");
        chShape = new Choice();
        chShape.add("Kwadrat");
        chShape.add("Koło");
        chShape.add("Trapez");
        btAdd = new Button("Dodaj");
        btDel = new Button("Usuń");
        btInfo = new Button("Info");
        ppanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        ppanel.add(labChoice,constraints);
        add(ppanel);









    }
}

