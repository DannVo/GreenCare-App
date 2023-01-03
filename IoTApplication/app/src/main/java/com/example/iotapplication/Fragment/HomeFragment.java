package com.example.iotapplication.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.budiyev.android.codescanner.CodeScanner;
import com.example.iotapplication.API.UserAPIs;
import com.example.iotapplication.Adapter.AddNewDevice.GetAllInfo;
import com.example.iotapplication.Adapter.ItemAdapter;
import com.example.iotapplication.AddDeviceActivity;
import com.example.iotapplication.EditDeviceActivity;
import com.example.iotapplication.GetPostActivity;
import com.example.iotapplication.MainActivity;
import com.example.iotapplication.PlantDetail;
import com.example.iotapplication.QrcodeActivity;
import com.example.iotapplication.R;
import com.example.iotapplication.SampleModel;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.getbase.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment implements ItemAdapter.plantOnClick {


    RecyclerView recyclerView;
    List<SampleModel> sampleholder = new ArrayList<>();
    List<SampleModel> tempholder = new ArrayList<>();
    ArrayList allDescription = new ArrayList<>();
    TextView tvDate,t1,t2,tvEmpty, tvTotalPlant;
    ItemAdapter itemAdapter;
    SampleModel sampleModel;
    ImageView ivLikeIcon,ivEditPen;
    Dialog addDialog;
    ConstraintLayout clQrcode,clText;

    Toolbar toolbar;
    ShimmerFrameLayout shimmerFrameLayout;
    SharedPreferences sharedPreferences, homeSharedPref;
    Context context;
    View view;
    FloatingActionButton floatButtQR, floatButtAdd;
    Button butt,btnASyncPage;
    CodeScanner codeScanner;

    public static final String fileNameHome = "home";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    public void showPopUpAdd(View v){
        addDialog.setContentView(R.layout.popup_add_device);

        clQrcode = (ConstraintLayout) addDialog.findViewById(R.id.clQrcode);
        clText = (ConstraintLayout) addDialog.findViewById(R.id.clText);


        clQrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), QrcodeActivity.class);
                startActivity(intent);
            }
        });
        clText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddDeviceActivity.class);
                startActivity(intent);
            }
        });
        addDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        addDialog.show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);
        context = container.getContext();
//        floatButtQR = view.findViewById(R.id.floatButtQR);
//
//        floatButtQR.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                IntentIntegrator intentIntegrator = new IntentIntegrator(
//                        getActivity()
//                );
//
//
//
//                Log.d("Test", "onClick: hahahah");
//
//                intentIntegrator.setPrompt("For flash use volume up key");
//
//                intentIntegrator.setBeepEnabled(true);
//                intentIntegrator.setOrientationLocked(true);
//                intentIntegrator.setCaptureActivity(QrcodeActivity.class);
//                intentIntegrator.initiateScan();
//
//
//
//            }
//        });


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);
        addDialog = new Dialog(getActivity());
        homeSharedPref = context.getSharedPreferences(fileNameHome, Context.MODE_PRIVATE);


//        floatButtQR.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), QrcodeActivity.class);
//                startActivity(intent);
//            }
//        });

        floatButtAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopUpAdd(view);
            }
        });

        btnASyncPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), GetPostActivity.class));
            }
        });






//
        try {
            getAllTask();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

//    @Deprecated
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//
//    }
//
//
//
//    }

    public void init(View view) {

        recyclerView = view.findViewById(R.id.recycler_item);
        shimmerFrameLayout = view.findViewById(R.id.shimmerLayoutHorizontal);
        tvDate = view.findViewById(R.id.tvDate);
        shimmerFrameLayout.startShimmer();
        btnASyncPage = view.findViewById(R.id.btnASyncPage);

        floatButtAdd = view.findViewById(R.id.floatButtAdd);
        t1 = view.findViewById(R.id.t1);
        t2 = view.findViewById(R.id.t2);
        tvEmpty = view.findViewById(R.id.tvEmpty);
        tvTotalPlant = view.findViewById(R.id.tvTotalPlant);
        ivLikeIcon = view.findViewById(R.id.likeReact);
        ivEditPen = view.findViewById(R.id.editPen);
    }

    public void getAllTask() throws JSONException {


        try {


            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {

                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);

                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
                    recyclerView.setNestedScrollingEnabled(false);

                    sampleholder = new ArrayList<>();

                    Calendar calendar = Calendar.getInstance();
                    String temp = DateFormat.getDateInstance().format(calendar.getTime());

                    sharedPreferences = context.getSharedPreferences(MainActivity.fileName, Context.MODE_PRIVATE);



                    String newValue = sharedPreferences.getString("user_id","");
                    Log.e("TAG", "response 44: " + newValue);
                    //GET all information
                    UserAPIs.userApi.callAllInfo(newValue).enqueue(new Callback<GetAllInfo>() {
                        @Override
                        public void onResponse(Call<GetAllInfo> call, Response<GetAllInfo> response) {
//                    Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                            Log.e("TAG", "response 44: " + response.toString());
                            if(response.isSuccessful()) {
                                GetAllInfo deviceResponse = response.body();
                                Integer sizeData = deviceResponse.getData().size();
                                SharedPreferences.Editor homeEditor = homeSharedPref.edit();
                                homeEditor.putInt("amount_device",sizeData);
                                homeEditor.commit();

//                                Log.e("TAG", "response 04: " + deviceResponse.getData().get(0).getDesc());

                                Log.e("TAG", "response 00: " + sizeData);

//                                 = null;
                                //CUT description of devices
                                if (deviceResponse.getData().size()>0){
                                    tvTotalPlant.setText("(you have "+sizeData+" plants)");
                                    for (int i = 0; i < sizeData; i++) {
                                        String deviceName = deviceResponse.getData().get(i).getName();
                                        String deviceDesc = deviceResponse.getData().get(i).getDesc();
                                        Log.e("TAG", "response 04: " + deviceDesc);

                                        if (deviceDesc == null){
                                            deviceDesc = "Empty Detail";
                                        }
                                        Log.e("TAG", "response 04: " + deviceDesc);
                                        SampleModel obj = new SampleModel(R.drawable.tree_sample, deviceName, cutString(deviceDesc), temp);

                                        tempholder.add(obj);
//                                    obj.setDesc(deviceDesc);
                                    }
                                    itemAdapter = new ItemAdapter(context, tempholder, HomeFragment.this);
                                    recyclerView.setAdapter(itemAdapter);
                                }else{
                                    tvEmpty.setText("No added device");
                                }



                                //FULL desciption of devices
//                                sampleholder.clear();
//                                for (int i = 0; i < deviceResponse.size(); i++) {
////                                    sampleholder.remove(i);
//                                    String deviceName = deviceResponse.get(i).getName();
//                                    String deviceDesc = deviceResponse.get(i).getDescription();
//
//                                    SampleModel obj1 = new SampleModel(R.drawable.tree_sample, deviceName, deviceDesc, temp);
//                                    sampleholder.add(obj1);
//                                }



//                                Log.e("TAG", "response 55: " + deviceResponse.get(0).getId());
//                                Log.e("TAG", "response 55: " + response.toString());
                                Toast.makeText(getContext(), "Upload data successfully", Toast.LENGTH_SHORT).show();


                            }else{
                                Toast.makeText(getActivity(), "Cannot upload data", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<GetAllInfo> call, Throwable t) {
                            Toast.makeText(getActivity(), "Login Fail", Toast.LENGTH_SHORT).show();
                            Log.e("ErrorLogin Message: ",t.getMessage());
                        }
                    });

                }
            }, 1500);

            ivLikeIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            changeLikeIcon();
                        }
                    },0);

                }
            });

            ivEditPen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getActivity(), EditDeviceActivity.class));
                }
            });

        } catch (Exception e) {
            Log.e("Exception", String.valueOf(e));
            e.printStackTrace();
        }
    }

    @Override
    public void plantOnClick(SampleModel sampleModel) {

        Intent data = new Intent(context, PlantDetail.class);
        data.putExtra("data", sampleModel);
//        data.putExtra("detail_desc",allDescription);

        startActivity(data);
//        Intent intent = new Intent(getActivity(), PlantDetail.class)
//        startActivity(new Intent(HomeFragment.this, PlantDetail.class).putExtra("data", sampleModel));
    }
    public void changeLikeIcon(){
        ivLikeIcon.setImageResource(R.drawable.red_heart);
    }

    public String cutString(String str){
        String newStr = str;
        if (str.length()>30){
            newStr = str.substring(0,30) + "...";

        }
        return newStr;

    }
}
//        simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
//            @Override
//            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//                return false;
//            }
//
//            @Override
//            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//
//                final int position = viewHolder.getAdapterPosition();
//                Todo deletedTodo = null;
//
//                switch (direction) {
//                    case ItemTouchHelper.LEFT:
//                        deletedTodo = listTodo.get(position);
//
//                        editor.remove("updateTitle");
//                        editor.remove("updateDescription");
//                        editor.remove("updateStartDate");
//                        editor.remove("updateEndDate");
//                        editor.putBoolean("updateStatus", true);
//                        editor.apply();
//
//                        updateTodo(deletedTodo.getId());
//
//                        listTodo.remove(deletedTodo);
//                        adapter.notifyItemRemoved(position);
//
//                        try {
//                            makeProgressTask(listTodo, paginatorInfo);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                        Todo finalDeletedTodo = deletedTodo;
//                        Snackbar.make(rvTodos, deletedTodo.getName(), Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                updateTodoFalse(finalDeletedTodo.getId());
//                                listTodo.add(position, finalDeletedTodo);
//                                adapter.notifyItemInserted(position);
//                            }
//                        }).show();
//                        break;
//                    case ItemTouchHelper.RIGHT:
//
//                        break;
//                }
//            }
//        }
