package com.elephantcarpaccio.model;

import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Model representation of a for a StateTax.
 */
public class StateTax {

    // Model Keys
    private static final String KEY_STATE = "state";
    private static final String KEY_TAX = "tax";

    private String state;

    private double taxRate;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    /**
     * Override the default return value to the txtvwHeaderName of this StateTax.
     *
     * @return String object that represents the txtHeaderTotal txtvwHeaderName.
     */
    @NonNull
    @Override
    public String toString() {
        return getState();
    }

    /**
     * Method creates a {@link List<StateTax>} from a JSON string.
     *
     * @param json String object that represents JSON data
     * @return A {@link List<StateTax>} object
     * @throws JSONException If the JSON object is missing any required keys.
     */
    public static List<StateTax> createStateTaxList(String json) throws JSONException {
        JSONArray array = new JSONArray(json);
        return createStateTaxes(array);
    }

    /**
     * Method creates a collection of {@link StateTax} objects from a JSONArray.
     *
     * @param array a JSONArray
     * @return A collection of {@link StateTax} objects or null if the parsing failed.
     * @throws JSONException When parameters are missing
     */
    private static List<StateTax> createStateTaxes(JSONArray array) throws JSONException {
        if (array == null) {
            return null;
        }

        int count = array.length();
        ArrayList<StateTax> stateTaxes = new ArrayList<>(count);

        for (int i = 0; i < count; ++i) {
            JSONObject jsonObject = array.getJSONObject(i);
            stateTaxes.add(createStateTax(jsonObject));
        }

        return stateTaxes;
    }

    /**
     * Method creates a {@link StateTax} from a JSON string.
     *
     * @param jsonObject JSONObject that contains Question data.
     * @return A {@link StateTax} object
     * @throws JSONException When parameters are missing
     */
    private static StateTax createStateTax(JSONObject jsonObject) throws JSONException {
        StateTax question = new StateTax();
        question.setState(jsonObject.getString(StateTax.KEY_STATE));
        question.setTaxRate(jsonObject.getDouble(StateTax.KEY_TAX));

        return question;
    }
}
