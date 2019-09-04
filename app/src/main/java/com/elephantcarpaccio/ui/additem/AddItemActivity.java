package com.elephantcarpaccio.ui.additem;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.elephantcarpaccio.R;
import com.elephantcarpaccio.db.AppDatabase;
import com.elephantcarpaccio.model.Item;
import com.elephantcarpaccio.model.StateTax;
import com.elephantcarpaccio.utils.Constants;

import java.util.List;

public class AddItemActivity extends AppCompatActivity implements AddItemContract.View {

    private EditText etItemName;
    private EditText etItemUnitPrice;
    private EditText etItemQuantity;
    private Spinner spState;
    private Button btnSave;
    private AddItemContract.Presenter userInputPresenter;
    private Item item;
    private List<String> stateTaxList;
    private boolean isForEdit;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_input);


        initialiseUI();

        item = new Item();

        AppDatabase database = AppDatabase.getDatabase(getApplication());

        userInputPresenter = new AddItemPresenter(this, database.itemModel(), database.stateTaxModel());

        setSpinnerDataForState();

        btnSave.setOnClickListener((View v) -> validateAndSubmitItem());

        isForEdit = isForEdit();

        if (isForEdit) {
            userInputPresenter.retrieveItem(item.id);
            toolbar.setTitle(getResources().getString(R.string.update_item));
        } else {
            toolbar.setTitle(getResources().getString(R.string.add_item));
        }
    }

    private boolean isForEdit() {
        if (getIntent().getExtras() != null) {
            item.id = getIntent().getLongExtra(Constants.ITEM_ID, 0);
            return true;
        }
        return false;
    }

    // Initialise all UI components
    private void initialiseUI() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);

        etItemName = findViewById(R.id.et_lable_name);
        etItemUnitPrice = findViewById(R.id.et_unit_price);
        etItemQuantity = findViewById(R.id.et_quantity);
        spState = findViewById(R.id.spinner_state_code);
        btnSave = findViewById(R.id.btn_save);
    }

    /**
     * Validate all the input field and save the Item object.
     */
    private void validateAndSubmitItem() {
        String itemName = etItemName.getText().toString().trim();
        String itemQuantity = etItemQuantity.getText().toString().trim();
        String itemPrice = etItemUnitPrice.getText().toString().trim();
        String state = (String) spState.getSelectedItem();

        if (!userInputPresenter.validateItemName(itemName)) {
            etItemName.setError(getString(R.string.please_enter_name));
            setFocus(etItemName);
            return;
        }

        if (!userInputPresenter.validateItemQuantity(itemQuantity)) {
            etItemQuantity.setError(getString(R.string.please_enter_quantity));
            setFocus(etItemQuantity);
            return;
        }

        if (!userInputPresenter.validateUnitPrice(itemPrice)) {
            etItemUnitPrice.setError(getString(R.string.please_enter_price));
            setFocus(etItemUnitPrice);
            return;
        }

        if (!userInputPresenter.validateState(state)) {
            showInputError(getString(R.string.please_enter_state));
            setFocus(spState);
            return;
        }

        //Create the user Item and save.
        item.setItemName(itemName);
        item.setItemQuantity(Integer.parseInt(itemQuantity));
        item.setItemUnitPrice(Double.parseDouble(itemPrice));
        item.setState(state);

        if (isForEdit) {
            userInputPresenter.updateItem(item);
            return;
        }
        userInputPresenter.saveItem(item);
    }

    /**
     * Create collection of {@link StateTax}) from json file.
     * <p>
     * Data set to the spinner
     */
    private void setSpinnerDataForState() {
        stateTaxList = userInputPresenter.getAllStates();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(AddItemActivity.this,
                android.R.layout.simple_spinner_item, stateTaxList);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spState.setAdapter(adapter);
    }

    private void setFocus(View view) {
        view.requestFocus();
    }

    private void showInputError(String message) {
        Toast.makeText(AddItemActivity.this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void close() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void displayUI(Item item) {
        etItemName.setText(item.getItemName());
        etItemQuantity.setText(String.valueOf(item.getItemQuantity()));
        etItemUnitPrice.setText(String.valueOf(item.getItemUnitPrice()));
        spState.setSelection(stateTaxList.indexOf(item.getState()));
    }
}
