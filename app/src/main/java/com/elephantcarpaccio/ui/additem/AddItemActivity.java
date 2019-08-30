package com.elephantcarpaccio.ui.additem;

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
import com.elephantcarpaccio.model.Item;
import com.elephantcarpaccio.model.StateTax;
import com.elephantcarpaccio.utils.CartItemUtils;
import com.elephantcarpaccio.utils.StringUtils;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class AddItemActivity extends AppCompatActivity implements AddItemContract.View {

    private EditText etItemName;
    private EditText etItemUnitPrice;
    private EditText etItemQuantity;
    private Spinner spState;
    private Button btnSave;
    private AddItemContract.Presenter userInputPresenter;
    private List<StateTax> stateTaxList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_input);

        initialiseUI();

        setSpinnerDataForState();

        userInputPresenter = new AddItem(this, CartItemUtils.getInstance());

        btnSave.setOnClickListener((View v) -> validateAndSubmitItem());
    }

    // Initialise all UI components
    private void initialiseUI() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

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
        StateTax stateTax = (StateTax) spState.getSelectedItem();

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

        if (!userInputPresenter.validateState(stateTax)) {
            showInputError(getString(R.string.please_enter_state));
            setFocus(spState);
            return;
        }

        //Create the user Item and save.
        Item item = new Item();
        item.setItemName(itemName);
        item.setItemQuantity(Integer.parseInt(itemQuantity));
        item.setItemUnitPrice(Double.parseDouble(itemPrice));
        item.setStateTax(stateTax);
        userInputPresenter.saveItem(item);
    }

    /**
     * Create collection of {@link StateTax}) from json file.
     * <p>
     * Data set to the spinner
     */
    private void setSpinnerDataForState() {
        try {
            String stateTaxes = StringUtils.readRawResource(this, R.raw.state_tax);
            stateTaxList = StateTax.createStateTaxList(stateTaxes);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        ArrayAdapter<StateTax> adapter = new ArrayAdapter<>(AddItemActivity.this,
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
}
