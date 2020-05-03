package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class Board extends AppCompatActivity implements View.OnClickListener {
    /**
     * dogP is the point counter for Dogs and the catP is the point count for cats.
     */
    private int dogP, catP;


    /**
     *  Array of the buttons by cell position/coordinates.
     */
    private ImageButton[][] buttons = new ImageButton[6][4];

    /**
     *  Keeps track of which round is. When it's even it's the turn of the Dog, otherwise is cat.
     */
    private int roundCount;

    /**
     *  Keeps track of the captured cells. Only stores "dog" when a dog has captured it,
     *  "cat" when a cat has captured it, and "" when it's not captured.
     */
    private String[][] petCell = new String[6][4];

    private TextView txtViewP1;
    private TextView txtViewP2;

    /**
     *  Stores the urls of the selected dog and cat from the intent.
     */
    private String[] urls;

    /**
     *  Stores Dog URL.
     */
    private String dogUrl;

    /**
     *  Stores the Cat URL.
     */
    private String catUrl;

    /**
     *  Initializes the game by setting up the count as zero, populating the petCell array to ""
     *  setting the listener on the buttons, getting the intent, and setting up the button for going
     *  back to the selection screen.
     *
     * @param savedInstanceState default?
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        txtViewP1 = findViewById(R.id.text_view_p1);
        txtViewP2 = findViewById(R.id.text_view_p2);

        roundCount = 0;

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                petCell[i][j] = "";

                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(Board.this);
            }
        }

        urls = getIntent().getStringArrayExtra("URLs");
        unpackUrls();

        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();

            }
        });
    }

    /**
     * Checks if it's valid if not then it just returns without increasing the count.
     *
     * If it's valid, then it checks which turn is it and captures the cell in the petCell array
     * of the pet and loads the image onto the button. Finally, it increases the count.
     *
     * @param v button that has been clicked.
     */
    @Override
    public void onClick(View v) {
        ImageButton pressed = (ImageButton) v;
        int[] capturedCell = getButtonID(v.getId());
        boolean canTake = valid(capturedCell);

        if (!canTake) {
            return;
        }

        if (roundCount % 2 == 0) {
            (petCell[capturedCell[0]][capturedCell[1]]) = "dog";
            Picasso.get().load(dogUrl).resize(247,229).into(pressed);

        } else if (roundCount % 2 == 1) {
            (petCell[capturedCell[0]][capturedCell[1]]) = "cat";
            Picasso.get().load(catUrl).resize(247,229).into(pressed);
        }

        roundCount++;
        if (checkForWin()) {
            if (roundCount % 2 == 0) {
                gatosWin();
            } else if (roundCount % 2 != 0) {
                doggosWin();
            }
        }  else if (roundCount == 24) {
            draw();
        }
    }

    private boolean checkForWin() {

        for (int i = 0; i < 6; i++) {
            if (petCell[i][0].equals(petCell[i][1])
                    && petCell[i][0].equals(petCell[i][2]) && petCell[i][0].equals(petCell[i][3])
                    && !petCell[i][0].equals("")) {
                return true;
            }

        }

        for (int i = 0; i < 4; i++) {
            if (petCell[0][i].equals(petCell[1][i])
                    && petCell[0][i].equals(petCell[2][i]) && petCell[0][i].equals(petCell[3][i]) && petCell[0][i].equals(petCell[4][i])
                    && petCell[0][i].equals(petCell[5][i]) && !petCell[0][i].equals("")) {
                return true;
            }

        }


        return false;
    }

    /**
     * Gets the URLs from the intent ans stores it in the dogUrl and catUrl that will be used
     * for loading the image on the clicked buttons.
     */
    private void unpackUrls() {
        dogUrl = urls[0];
        catUrl = urls[1];
    }

    /**
     * Returns the position in the array that has been clicked.
     * @param id button that has been clicked.
     * @return the position in the array that has been clicked.
     */
    private int[] getButtonID(int id) {
        int[] toReturn = new int[2];

        switch (id) {
            case R.id.button_00:
                toReturn[0] = 0;
                toReturn[1] = 0;
                return toReturn;

            case R.id.button_01:
                toReturn[0] = 0;
                toReturn[1] = 1;
                return toReturn;

            case R.id.button_02:
                toReturn[0] = 0;
                toReturn[1] = 2;
                return toReturn;

            case R.id.button_03:
                toReturn[0] = 0;
                toReturn[1] = 3;
                return toReturn;

            case R.id.button_10:
                toReturn[0] = 1;
                toReturn[1] = 0;
                return toReturn;

            case R.id.button_11:
                toReturn[0] = 1;
                toReturn[1] = 1;
                return toReturn;

            case R.id.button_12:
                toReturn[0] = 1;
                toReturn[1] = 2;
                return toReturn;

            case R.id.button_13:
                toReturn[0] = 1;
                toReturn[1] = 3;
                return toReturn;

            case R.id.button_20:
                toReturn[0] = 2;
                toReturn[1] = 0;
                return toReturn;

            case R.id.button_21:
                toReturn[0] = 2;
                toReturn[1] = 1;
                return toReturn;

            case R.id.button_22:
                toReturn[0] = 2;
                toReturn[1] = 2;
                return toReturn;

            case R.id.button_23:
                toReturn[0] = 2;
                toReturn[1] = 3;
                return toReturn;

            case R.id.button_30:
                toReturn[0] = 3;
                toReturn[1] = 0;
                return toReturn;

            case R.id.button_31:
                toReturn[0] = 3;
                toReturn[1] = 1;
                return toReturn;

            case R.id.button_32:
                toReturn[0] = 3;
                toReturn[1] = 2;
                return toReturn;

            case R.id.button_33:
                toReturn[0] = 3;
                toReturn[1] = 3;
                return toReturn;

            case R.id.button_40:
                toReturn[0] = 4;
                toReturn[1] = 0;
                return toReturn;

            case R.id.button_41:
                toReturn[0] = 4;
                toReturn[1] = 1;
                return toReturn;

            case R.id.button_42:
                toReturn[0] = 4;
                toReturn[1] = 2;
                return toReturn;

            case R.id.button_43:
                toReturn[0] = 4;
                toReturn[1] = 3;
                return toReturn;

            case R.id.button_50:
                toReturn[0] = 5;
                toReturn[1] = 0;
                return toReturn;

            case R.id.button_51:
                toReturn[0] = 5;
                toReturn[1] = 1;
                return toReturn;

            case R.id.button_52:
                toReturn[0] = 5;
                toReturn[1] = 2;
                return toReturn;

            case R.id.button_53:
                toReturn[0] = 5;
                toReturn[1] = 3;
                return toReturn;
        }
        return null;
    }

    /**
     * @param taken position of the array that wants to be captured.
     * @return false if can't be captured, true if can be.
     */
    private boolean valid(int[] taken) {
        return (petCell[taken[0]][taken[1]]).equals("");



    }

    private void doggosWin() {
        dogP++;
        Toast.makeText(this, "Doggos Litty!", Toast.LENGTH_SHORT).show();
        updatePoints();
        resetBoard();
    }

    private void gatosWin() {
        catP++;
        Toast.makeText(this, "Gatos Litty!", Toast.LENGTH_SHORT).show();
        updatePoints();

        resetBoard();
    }

    private void draw() {
        Toast.makeText(this, "SRSLY A DRAW?", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void updatePoints() {
        txtViewP1.setText("Doggos : " + dogP);
        txtViewP2.setText("Gatos : " + catP);
    }

    private void resetBoard() {

        
        roundCount = 0;
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }



}
