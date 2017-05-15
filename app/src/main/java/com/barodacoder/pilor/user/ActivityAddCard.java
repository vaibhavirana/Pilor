package com.barodacoder.pilor.user;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.barodacoder.pilor.ActivityBase;
import com.barodacoder.pilor.R;
import com.barodacoder.pilor.model.BookService;

public class ActivityAddCard extends ActivityBase
{
    private Toolbar toolbar;

    private LinearLayout llAddCard, llDeleteCard;

    private Button btnAddCard, btnDeleteCard;

    private TextView tvCardNumber;
    private BookService bookService;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setStatusBarGradiant(this);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_card);

        initData();

        initUi();
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();

        overridePendingTransition(R.anim.slide_in_from_left_to_right, R.anim.slide_out_from_left_to_right);

        finish();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        initUi();
    }

    protected void initData()
    {
        super.initData();

        bookService =null;

        bookService = (BookService) getIntent().getSerializableExtra("BOOK_SERVICE");

    }

    private void initUi()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.back_icon);

        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });

        ((TextView) findViewById(R.id.tvTitle)).setTypeface(appData.getFontMedium());

        ((TextView) findViewById(R.id.tvAddMsg)).setTypeface(appData.getFontRegular());
        ((TextView) findViewById(R.id.tvStoredMsg)).setTypeface(appData.getFontRegular());

        llAddCard = (LinearLayout) findViewById(R.id.llAddCard);

        llDeleteCard = (LinearLayout) findViewById(R.id.llDeleteCard);

        tvCardNumber = (TextView) findViewById(R.id.tvCardNumber);
        tvCardNumber.setTypeface(appData.getFontRegular());

        btnAddCard = (Button) findViewById(R.id.btnAddCard);
        btnAddCard.setTypeface(appData.getFontRegular());

        btnAddCard.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                goToCreatePaymentScreen(bookService);
            }
        });

        btnDeleteCard = (Button) findViewById(R.id.btnDeleteCard);
        btnDeleteCard.setTypeface(appData.getFontRegular());

        btnDeleteCard.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                showDeleteCardAlertMsg(getString(R.string.txt_delete_credit_card), getString(R.string.txt_are_you_sure));
            }
        });

        if(libFile.getCardNumber().equals(""))
        {
            llAddCard.setVisibility(View.VISIBLE);

            llDeleteCard.setVisibility(View.GONE);
        }
        else
        {
            llAddCard.setVisibility(View.GONE);

            llDeleteCard.setVisibility(View.VISIBLE);

            tvCardNumber.setText(libFile.getCardNumber());
        }
    }

    private void showDeleteCardAlertMsg(String title, String msg)
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        dialogBuilder.setPositiveButton(getString(R.string.txt_yes), new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                libFile.setCardNumber("");

                initUi();

                dialog.dismiss();
            }
        });

        dialogBuilder.setNegativeButton(getString(R.string.txt_no), new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });

        dialogBuilder.setTitle(title);

        dialogBuilder.setMessage(msg);

        dialogBuilder.show();
    }



}
