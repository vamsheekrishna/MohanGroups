package com.example.mybusinesstracker.customer.ui.customer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.DataBindingUtil;

import com.example.mybusinesstracker.BaseCalsses.BaseFragment;
import com.example.mybusinesstracker.R;
import com.example.mybusinesstracker.customer.CustomerFragmentInteractionListener;
import com.example.mybusinesstracker.databinding.FragmentCreateCustomerBinding;


public class CreateCustomer extends BaseFragment implements View.OnClickListener, View.OnTouchListener {
    private static final String ARG_CUSTOMER = "customer";

    private Customer mCustomer;

    private CustomerFragmentInteractionListener mListener;
    private AppCompatImageView bgImage;
    private Bitmap bitmap;
    private AppCompatImageView pickedColor;
    boolean isUpdateCustomer =false;
    public CreateCustomer() {
        // Required empty public constructor
    }
    FragmentCreateCustomerBinding binding;
    public static CreateCustomer newInstance(Customer param1) {
        CreateCustomer fragment = new CreateCustomer();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CUSTOMER, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCustomer = (Customer) getArguments().getSerializable(ARG_CUSTOMER);
        }
    }
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_customer, container, false);
        View view = binding.getRoot();

        bgImage = view.findViewById(R.id.color_picker_img);
         bitmap = BitmapFactory.decodeResource(this.getResources(),R.drawable.color_pic);
        bgImage.setImageBitmap(bitmap);
        bgImage.setOnTouchListener(this);

        pickedColor = view.findViewById(R.id.image_bg);

        if(null == mCustomer) {
            mCustomer = new Customer();
            binding.setCustomer(mCustomer);
            isUpdateCustomer =false;
        } else {
            binding.setCustomer(mCustomer);
            isUpdateCustomer = true;
            view.findViewById(R.id.delete_customer).setVisibility(View.VISIBLE);
            view.findViewById(R.id.delete_customer).setOnClickListener(this);
            ((Button)view.findViewById(R.id.insert_customer)).setText(getString(R.string.cus_update_btn));
            pickedColor.setBackgroundColor(mCustomer.getColorID());
        }
        view.findViewById(R.id.insert_customer).setOnClickListener(this);
        view.findViewById(R.id.image_bg).setOnTouchListener(this);
        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CustomerFragmentInteractionListener) {
            mListener = (CustomerFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement CustomerFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.insert_customer:
                mListener.createCustomer(binding.getCustomer(), isUpdateCustomer);
                break;
            case R.id.delete_customer:
                mListener.deleteCustomer(binding.getCustomer(), this);
                break;
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        try {
            if (v.getId() == R.id.color_picker_img) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    Drawable imgDrawable = ((ImageView) v).getDrawable();
                    //imgDrawable will not be null if you had set src to ImageView, in case of background drawable it will be null
                    Bitmap bitmap = ((BitmapDrawable) imgDrawable).getBitmap();

                    Matrix inverse = new Matrix();
                    ((ImageView) v).getImageMatrix().invert(inverse);
                    float[] touchPoint = new float[]{event.getX(), event.getY()};
                    inverse.mapPoints(touchPoint);
                    int xCoord = (int) touchPoint[0];
                    int yCoord = (int) touchPoint[1];

                    if(xCoord>=0 && yCoord>=0) {
                        int touchedRGB = bitmap.getPixel(xCoord, yCoord);
                        //then do what you want with the pixel data, e.g
                        int redValue = Color.red(touchedRGB);
                        int greenValue = Color.green(touchedRGB);
                        int blueValue = Color.blue(touchedRGB);
                        int alphaValue = Color.alpha(touchedRGB);

                        if(redValue >=0 && greenValue >=0 && blueValue >=0 && alphaValue >=0 ) {

                            int colorValue = Color.argb(alphaValue, redValue, greenValue, blueValue);
                            Log.i("TouchedColor", "TouchedRGB: " + touchedRGB);
                            Log.i("TouchedColor", "RedValue: " + redValue);
                            Log.i("TouchedColor", "GreenValue: " + greenValue);
                            Log.i("TouchedColor", "BlueValue: " + blueValue);
                            Log.i("TouchedColor", "AlphaValue: " + alphaValue);
                            Log.i("TouchedColor", "ColorValue ARGB: " + colorValue);

                            //String color = "#" + Integer.toHexString();

                            mCustomer.setColorID(Color.argb(alphaValue,redValue,greenValue,blueValue));

                            pickedColor.setBackgroundColor(mCustomer.getColorID());
                        }
                    }
                    return false;

                }


            }
        }catch (Exception e) {
            Log.d("Exception: ","Exception: "+e.getMessage());
        }
        return true;
    }
}
