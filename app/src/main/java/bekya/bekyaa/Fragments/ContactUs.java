package bekya.bekyaa.Fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import bekya.bekyaa.Activites.Home;
import bekya.bekyaa.BuildConfig;
import bekya.bekyaa.R;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactUs extends Fragment {


    public ContactUs() {
        // Required empty public constructor
    }
    View view;
    Button Btn_Chat;
    TextView ContactUs,T_Advertisement,T_Customer,T_Version;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_contact_us, container, false);
//         Btn_Chat=view.findViewById(R.id.Btn_Chat);
        ContactUs=view.findViewById(R.id.ContactUs);
        T_Advertisement=view.findViewById(R.id.T_Advertisement);
        ContactUs=view.findViewById(R.id.ContactUs);
        T_Version=view.findViewById(R.id.T_Version);
        T_Customer=view.findViewById(R.id.T_Customer);
        LinearLayout developed_company =  view.findViewById(R.id.developed_company);

        Home.T_Title.setText(getActivity().getResources().getString(R.string.aboutt));
        Home.Rela_Govern.setVisibility(View.GONE);
        String versionName = BuildConfig.VERSION_NAME;
        T_Version.setText(versionName);
//        Btn_Chat.setOnClickListener(new View.OnClickListener() {
//             @Override
//             public void onClick(View view) {
//                 Chat detailsHomeProductFragment = new Chat();
//                 Bundle bundle = new Bundle();
//                 detailsHomeProductFragment.setArguments(bundle);
//                 getFragmentManager().beginTransaction().replace(R.id.Rela_Contact, detailsHomeProductFragment)
//                         .addToBackStack(null).commit();
//
//             }
//         });
        T_Advertisement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","Info@tempomena.com", null));
                startActivity(Intent.createChooser(emailIntent, "Send email..."));

            }
        });

        ContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","Adv@tempomena.com", null));
                startActivity(Intent.createChooser(emailIntent, "Send email..."));

            }
        });

        T_Customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","Support@tempomena.com", null));
                startActivity(Intent.createChooser(emailIntent, "Send email..."));

            }
        });

        developed_company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contact = "+20 1128753353"; // use country code with your phone number
                String url = "https://api.whatsapp.com/send?phone="+contact;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });




        return view;
    }

}
