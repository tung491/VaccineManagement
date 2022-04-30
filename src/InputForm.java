import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class InputForm extends JFrame {
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    boolean checkEmptyFields(List<JTextField> fields) {
        boolean haveEmptyFields = false;
        for (JTextField field: fields) {
            if (field.getText().equals("")) {  // if field is empty, set border to red
                field.setBorder(new LineBorder(Color.RED, 2));
                haveEmptyFields = true;
            } else {   // if field is not empty, set default border
                field.setBorder(new JTextField().getBorder());
            }
        }
        return !haveEmptyFields;
    }
}
