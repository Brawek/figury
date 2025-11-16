package myclass.gui;

import myclass.data.Circle;
import myclass.data.Square;
import myclass.data.Triangle;
// do NOT import myclass.data.Shape or Rectangle here to avoid ambiguity with java.awt
// we will reference them by full name where needed

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class Start extends Frame {
    private Button btAdd, btDel, btInfo;
    private Choice chShape;
    private MenuBar menuBar;
    private Menu mBase;
    private MenuItem itemNew, itemRead, itemSave, itemClose;
    private Panel lpanel, ppanel;
    private Label labLista, labChoice;
    private List listFig;

    // internal storage - fully qualified to avoid ambiguous 'Shape'
    private java.util.List<myclass.data.Shape> shapes = new ArrayList<>();

    public Start(String title) {
        super(title);
        setSize(700,500);
        setLayout(new BorderLayout(6,6));
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) { dispose(); }
        });
        // menu
        menuBar = new MenuBar();
        mBase = new Menu("BAZA");
        itemNew = new MenuItem("Nowa");
        itemRead = new MenuItem("Wczytaj");
        itemSave = new MenuItem("Zapisz");
        itemClose = new MenuItem("Zakończ");
        mBase.add(itemNew); mBase.add(itemRead); mBase.add(itemSave); mBase.add(itemClose);
        menuBar.add(mBase);
        setMenuBar(menuBar);

        // left panel - lista
        lpanel = new Panel(new BorderLayout());
        lpanel.setPreferredSize(new Dimension(300, 0));
        lpanel.setBackground(Color.LIGHT_GRAY);
        labLista = new Label("Lista figur");
        listFig = new List();
        lpanel.add(labLista, BorderLayout.NORTH);
        lpanel.add(listFig, BorderLayout.CENTER);
        add(lpanel, BorderLayout.WEST);

        // right panel - wybor i przyciski
        ppanel = new Panel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8,8,8,8);
        c.gridx = 0; c.gridy = 0; c.anchor = GridBagConstraints.WEST;

        labChoice = new Label("Wybierz figurę");
        ppanel.add(labChoice, c);

        chShape = new Choice();
        chShape.add("Kwadrat");
        chShape.add("Koło");
        chShape.add("Trójkąt");
        chShape.add("Prostokąt");
        c.gridy = 1;
        ppanel.add(chShape, c);

        btAdd = new Button("Dodaj");
        btDel = new Button("Usuń");
        btInfo = new Button("Info");
        Panel pBtns = new Panel(new GridLayout(3,1,0,8));
        pBtns.add(btAdd); pBtns.add(btDel); pBtns.add(btInfo);
        c.gridy = 2;
        ppanel.add(pBtns, c);

        add(ppanel, BorderLayout.CENTER);

        // small floating menu on left like in image
        Panel leftMenu = new Panel(new GridLayout(4,1,0,6));
        leftMenu.setPreferredSize(new Dimension(80,120));
        Button mNew = new Button("Nowa");
        Button mSave = new Button("Zapisz");
        Button mRead = new Button("Wczytaj");
        Button mClose = new Button("Koniec");
        leftMenu.add(mNew); leftMenu.add(mRead); leftMenu.add(mSave); leftMenu.add(mClose);
        add(leftMenu, BorderLayout.NORTH);

        // listeners
        btAdd.addActionListener(e -> onAdd());
        btDel.addActionListener(e -> onDelete());
        btInfo.addActionListener(e -> onInfo());

        itemNew.addActionListener(e -> onNew());
        itemRead.addActionListener(e -> onRead());
        itemSave.addActionListener(e -> onSave());
        itemClose.addActionListener(e -> dispose());

        // same for small left menu
        mNew.addActionListener(e -> onNew());
        mRead.addActionListener(e -> onRead());
        mSave.addActionListener(e -> onSave());
        mClose.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void refreshList() {
        listFig.removeAll();
        for (myclass.data.Shape s : shapes) listFig.add(s.toString());
    }

    private void onAdd() {
        String type = chShape.getSelectedItem();
        AddShapeDialog dlg = new AddShapeDialog(this, type);
        myclass.data.Shape created = dlg.getCreated();
        if (created != null) {
            shapes.add(created);
            refreshList();
        }
    }

    private void onDelete() {
        int idx = listFig.getSelectedIndex();
        if (idx < 0) {
            showMessage("Wybierz element do usunięcia.");
            return;
        }
        shapes.remove(idx);
        refreshList();
    }

    private void onInfo() {
        int idx = listFig.getSelectedIndex();
        if (idx < 0) { showMessage("Wybierz element, aby zobaczyć info."); return; }
        myclass.data.Shape s = shapes.get(idx);
        String msg = String.format("Nazwa: %s\nTyp: %s\nPole: %.4f", s.getName(), s.getType(), s.getArea());
        new InfoDialog(this, "Info", msg);
    }

    private void onNew() {
        shapes.clear();
        refreshList();
    }

    private void onSave() {
        FileDialog fd = new FileDialog(this, "Zapisz do pliku", FileDialog.SAVE);
        fd.setFile("shapes.txt");
        fd.setVisible(true);
        String dir = fd.getDirectory();
        String file = fd.getFile();
        if (dir == null || file == null) return;
        File f = new File(dir, file);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
            for (myclass.data.Shape s : shapes) {
                bw.write(s.toSaveString());
                bw.newLine();
            }
            showMessage("Zapisano pomyślnie.");
        } catch (IOException ex) {
            showMessage("Błąd zapisu: " + ex.getMessage());
        }
    }

    private void onRead() {
        FileDialog fd = new FileDialog(this, "Wczytaj plik", FileDialog.LOAD);
        fd.setFile("*.txt");
        fd.setVisible(true);
        String dir = fd.getDirectory();
        String file = fd.getFile();
        if (dir == null || file == null) return;
        File f = new File(dir, file);
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            shapes.clear();
            String line;
            while ((line = br.readLine()) != null) {
                // parse: typ;nazwa;param1;param2...
                String[] parts = line.split(";");
                if (parts.length < 2) continue;
                String typ = parts[0];
                String nazwa = parts[1];
                try {
                    switch (typ) {
                        case "Koło":
                            double r = Double.parseDouble(parts[2]);
                            shapes.add(new Circle(nazwa, r));
                            break;
                        case "Kwadrat":
                            double s = Double.parseDouble(parts[2]);
                            shapes.add(new Square(nazwa, s));
                            break;
                        case "Prostokąt":
                            double w = Double.parseDouble(parts[2]);
                            double h = Double.parseDouble(parts[3]);
                            shapes.add(new myclass.data.Rectangle(nazwa, w, h));
                            break;
                        case "Trójkąt":
                            double b = Double.parseDouble(parts[2]);
                            double th = Double.parseDouble(parts[3]);
                            shapes.add(new Triangle(nazwa, b, th));
                            break;
                        default:
                            // ignore unknown
                    }
                } catch (Exception ex) {
                    // skip malformed line
                }
            }
            refreshList();
            showMessage("Wczytano pomyślnie.");
        } catch (IOException ex) {
            showMessage("Błąd odczytu: " + ex.getMessage());
        }
    }

    private void showMessage(String msg) {
        new InfoDialog(this, "Komunikat", msg);
    }

    // simple dialog for info messages
    static class InfoDialog extends Dialog {
        public InfoDialog(Frame owner, String title, String msg) {
            super(owner, title, true);
            setLayout(new BorderLayout());
            TextArea ta = new TextArea(msg, 5, 30);
            ta.setEditable(false);
            add(ta, BorderLayout.CENTER);
            Panel p = new Panel();
            Button ok = new Button("OK");
            ok.addActionListener(e -> dispose());
            p.add(ok);
            add(p, BorderLayout.SOUTH);
            setSize(350,180);
            setLocationRelativeTo(owner);
            setVisible(true);
        }
    }
}
