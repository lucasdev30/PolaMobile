package ap4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dvd.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class Ap4Adapter extends ArrayAdapter<Ap4Model> {

    private Context context;
    private int resource;
    private List<Ap4Model> listeElectro;

    public Ap4Adapter(Context context, int resource, List<Ap4Model> listeElectro) {
        super(context, resource, listeElectro);
        this.context = context;
        this.resource = resource;
        this.listeElectro = listeElectro;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        }

        Ap4Model ap4 = listeElectro.get(position);

        // Image
        ImageView imageArtiste = convertView.findViewById(R.id.imageArtiste);
        imageArtiste.setImageResource(ap4.getImgArtiste());

        // Nom
        TextView nomArtiste = convertView.findViewById(R.id.nomArtiste);
        nomArtiste.setText(ap4.getNomArtiste());

        // Date
        TextView dateConcert = convertView.findViewById(R.id.dateConcert);

        // Vérification de la date et affichage si non null
        if (ap4.getDateConcert() != null) {
            SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRENCH);
            dateConcert.setText(outputDateFormat.format(ap4.getDateConcert()));
        } else {
            // Si la date est nulle, afficher un message par défaut
            dateConcert.setText("Date non disponible");
        }

        // Prix
        TextView prixConcert = convertView.findViewById(R.id.concertPrix);
        prixConcert.setText(String.format(Locale.FRENCH, "%.2f €", ap4.getPrixPlace()));


        return convertView;
    }
}

