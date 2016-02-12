/**
 * To use you must include xmlns:dice="http://schemas.android.com/apk/res/edu.uwf.tabletopgroupcustom_components"
 * in the layout you wish to use teh control in....
 * then to specify attributes you use dice:attribute_name="value" where dice is whatever you used in xmlns:<here> from above
 * finally, you added the control with <edu.uwf.tabletopgroup.custom_component.DiceView attributes />
 */

package edu.uwf.tabletopgroup.custom_components;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import edu.uwf.tabletopgroup.tabletop_squire.R;

/**
 * Created by qjrou on 2/11/2016.
 * Temporary extends TextView to use as an output for data and values

 */
public class DiceView extends TextView {

    ArrayList<Die> dice = new ArrayList<>();

    public DiceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        processAttribute(attrs);
    }

    private final void processAttribute(AttributeSet attrs) {
        TypedArray tArray = getContext().obtainStyledAttributes(attrs, R.styleable.dice_roll_view);
        int cnt = tArray.getIndexCount(); // count the attributes gives....so we can loop over them

        int faces = 6; // Default die type, 1d6
        int count = 1; // Default die type, 1d6

        for(int i = 0 ; i < cnt ; i++)
        {
            int attr = tArray.getIndex(i); // get the ith attribute from layout

            switch(attr) // Process the attribute, both attr and the R-values are ints...co switch works nicesly
            {
                case R.styleable.dice_roll_view_dice_faces:
                    faces = tArray.getInteger(attr, 6);
                    break;

                case R.styleable.dice_roll_view_dice_count:
                    count = tArray.getInteger(attr, 1);
                    break;
                case R.styleable.dice_roll_view_initial_roll:
                    break;
                case R.styleable.dice_roll_view_show_pips:
                    break;
                case R.styleable.dice_roll_view_distance_between_dice:
                    break;
            }
        }

        tArray.recycle(); // Cleanup attribs

        for(int i = 0 ; i < count ; i++)
        {
            dice.add(new Die(faces, new Random().nextInt((faces - 1) + 1) + 1)); // adda die with faces and random initial roll
        }
    }

    public int roll()
    {
        int sum = 0;

        for(Die i : dice)
        {
            sum += i.roll();
        }

        return sum;
    }

    private class Die
    {
        Random r = new Random();

        public int lastRoll = 0;
        public int faces = 0;

        public Die(int faces, int initialRoll)  {
            this.faces = faces;
            this.lastRoll = initialRoll;
        }

        public int roll()
        {
            r.setSeed(lastRoll);

            return r.nextInt((faces - 1 ) + 1) + 1;
        }

        public String toString()
        {
            return "[" + lastRoll + "]";
        }

        private void setFaces(int faces) { this.faces = faces; } // sets number of faces, done on construction
        private void setFace(int face) { this.lastRoll = face; }

        public int getFaces() {
            return faces;
        }
    }

    public String toString()
    {
        String result = new String();

        result.concat("[ ");

        for( Die die : dice)
        {
            result.concat("1d" + die.faces + "(Rolled: )" + die.lastRoll );

            if(dice.indexOf(die) == dice.size() - 1)
            {
               ;
            }
            else
            {
                result.concat(" : ");
            }
        }

        return result;
    }


}
