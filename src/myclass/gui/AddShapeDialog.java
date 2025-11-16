package myclass.gui;

import myclass.data.*;

import java.awt.*;
import java.awt.event.*;

public class AddShapeDialog extends Dialog {
    private TextField tfName;
    private TextField tfP1;
    private TextField tfP2;
    private Label lbP1;
    private Label lbP2;
    private Button btSave;
    private Button btCancel;
    private String selectedType;
    private myclass.data.Shape created = null;

    public AddShapeDialog(Frame owner, String type) {
        super(owner, "Dodaj figurę", true);
        selectedType = type;
        setSize(320,220);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6,6,6,6);
        c.fill = GridBagConstraints.HORIZONTAL;

        // Name
        c.gridx = 0; c.gridy = 0;
        add(new Label("Nazwa:"), c);
        tfName = new TextField();
        c.gridx = 1; c.gridy = 0; c.weightx = 1.0;
        add(tfName, c);

        // Param1
        lbP1 = new Label();
        c.gridx = 0; c.gridy = 1; c.weightx = 0.0;
        add(lbP1, c);
        tfP1 = new TextField();
        c.gridx = 1; c.gridy = 1; c.weightx = 1.0;
        add(tfP1, c);

        // Param2 (may be hidden)
        lbP2 = new Label();
        c.gridx = 0; c.gridy = 2; c.weightx = 0.0;
        add(lbP2, c);
        tfP2 = new TextField();
        c.gridx = 1; c.gridy = 2; c.weightx = 1.0;
        add(tfP2, c);

        // Buttons
        Panel pButtons = new Panel();
        pButtons.setLayout(new FlowLayout(FlowLayout.CENTER));
        btSave = new Button("Zapisz");
        btCancel = new Button("Anuluj");
        pButtons.add(btSave);
        pButtons.add(btCancel);
        c.gridx = 0; c.gridy = 3; c.gridwidth = 2; c.weightx = 0.0;
        add(pButtons, c);

        setupFieldsForType(type);

        // listeners
        btSave.addActionListener(e -> onSave());
        btCancel.addActionListener(e -> { created = null; dispose(); });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        setLocationRelativeTo(owner);
        setVisible(true);
    }

    private void setupFieldsForType(String type) {
        if (type.equals("Koło")) {
            lbP1.setText("Promień:");
            lbP2.setText("");
            tfP2.setVisible(false);
            lbP2.setVisible(false);
        } else if (type.equals("Kwadrat")) {
            lbP1.setText("Bok:");
            lbP2.setText("");
            tfP2.setVisible(false);
            lbP2.setVisible(false);
        } else if (type.equals("Prostokąt")) {
            lbP1.setText("Szerokość:");
            lbP2.setText("Wysokość:");
            tfP2.setVisible(true);
            lbP2.setVisible(true);
        } else if (type.equals("Trójkąt")) {
            lbP1.setText("Podstawa:");
            lbP2.setText("Wysokość:");
            tfP2.setVisible(true);
            lbP2.setVisible(true);
        } else {
            lbP1.setText("Param1:");
            lbP2.setText("Param2:");
            tfP2.setVisible(true);
            lbP2.setVisible(true);
        }
        validate();
    }

    private void onSave() {
        String name = tfName.getText().trim();
        if (name.isEmpty()) {
            showError("Podaj nazwę.");
            return;
        }
        try {
            if (selectedType.equals("Koło")) {
                double r = Double.parseDouble(tfP1.getText().trim());
                if (r <= 0) { showError("Promień musi być > 0"); return; }
                created = new Circle(name, r);

            } else if (selectedType.equals("Kwadrat")) {
                double s = Double.parseDouble(tfP1.getText().trim());
                if (s <= 0) { showError("Bok musi być > 0"); return; }
                created = new Square(name, s);

            } else if (selectedType.equals("Prostokąt")) {
                double w = Double.parseDouble(tfP1.getText().trim());
                double h = Double.parseDouble(tfP2.getText().trim());
                if (w <= 0 || h <= 0) { showError("Wymiary muszą być > 0"); return; }
                created = new myclass.data.Rectangle(name, w, h);

            } else if (selectedType.equals("Trójkąt")) {
                double b = Double.parseDouble(tfP1.getText().trim());
                double h = Double.parseDouble(tfP2.getText().trim());
                if (b <= 0 || h <= 0) { showError("Wymiary muszą być > 0"); return; }
                created = new Triangle(name, b, h);

            } else {
                showError("Nieznany typ.");
                return;
            }
            dispose();
        } catch (NumberFormatException ex) {
            showError("Błędny format liczby.");
        }
    }

    private void showError(String msg) {
        new MessageDialog(this, "Błąd", msg);
    }

    public myclass.data.Shape getCreated() {
        return created;
    }

    // small modal message dialog
    static class MessageDialog extends Dialog {
        public MessageDialog(Window owner, String title, String msg) {
            super(owner, title, ModalityType.APPLICATION_MODAL);
            setLayout(new BorderLayout(6,6));
            add(new Label(msg), BorderLayout.CENTER);
            Panel p = new Panel();
            Button ok = new Button("OK");
            p.add(ok);
            add(p, BorderLayout.SOUTH);
            ok.addActionListener(e -> dispose());
            setSize(280,120);
            setLocationRelativeTo(owner);
            setVisible(true);
        }
    }
}
