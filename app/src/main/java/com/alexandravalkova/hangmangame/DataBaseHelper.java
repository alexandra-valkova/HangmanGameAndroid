package com.alexandravalkova.hangmangame;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "hangman-dictionary.db";
    private static final String TABLE_WORDS = "Words";
    private static final String WORD_ID = "_id";
    private static final String WORD = "word";
    private static final String CREATE_TABLE_WORDS = "CREATE TABLE IF NOT EXISTS " + TABLE_WORDS + "('"
            + WORD_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT, '"
            + WORD + "' TEXT NOT NULL UNIQUE);";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Cursor cursor = null;
        try {
            db.execSQL(CREATE_TABLE_WORDS);
            cursor = db.query(TABLE_WORDS, new String[]{"*"}, null, null, null, null, null);
            if (!cursor.moveToNext()) {
                String[] words = ("ORANGE,HOLIDAY,BULB,PALTRY,FLESH,KILL,HEAT,SPOTLESS,HEARTBREAKING,SHORT,TRICK," +
                        "DISGUSTING,BELLS,STOVE,CONTINUE,TYPICAL,DECORATE,NICE,REGRET,TEACHING," +
                        "DISGUSTED,SAND,DELIGHT,ARM,NUTTY,CONFUSE,REPLY,TOUCH,CLIP,TRUTHFUL," +
                        "QUESTION,SUSPECT,DRIVING,EXPLODE,MATE,FLY,THAW,ABUSIVE,CHEAT,WOOD,NUMBER," +
                        "LAND,SIMPLE,HORSE,SPECTACULAR,CABBAGE,UNKNOWN,LEGAL,BADGE,VAGABOND,ROUTE," +
                        "SOOTHE,CAN,VENGEFUL,TICKET,STEEP,SECOND-HAND,DEER,CANNON,HOUSES,PART,LEAN,MAN," +
                        "TOE,SHADE,ROAD,SQUEAK,GIRL,NECESSARY,GHOST,FROG,HYPNOTIC,TRICKY,ELBOW,TEETH," +
                        "SAD,INFAMOUS,CUTE,HESITANT,LEG,STRING,COUNTRY,LARGE,SLEEPY,PRODUCTIVE,ZIP," +
                        "STAGE,BITE,STAMP,DOLL,ADHESIVE,CAUSE,FLIPPANT,REPLACE,MIDDLE,AIRPORT,THROAT," +
                        "BRAKE,FESTIVE,GLAMOROUS").split(",");

                String query = "INSERT INTO " + TABLE_WORDS + " (" + WORD + ") VALUES ('" +
                        (StringUtil.join("'),('", Arrays.asList(words))) + "')";

                db.execSQL(query);
            }

        } catch (SQLException e) {
            logException(e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Word getRandom() {
        Cursor cursor = null;
        SQLiteDatabase db = null;
        try {
            db = getReadableDatabase();
            cursor = db.query(TABLE_WORDS, new String[]{WORD_ID, WORD}, null, null, null, null, "RANDOM()", "1");
            if (cursor.moveToNext()) {
                return getWord(cursor);
            }
            return null;
        } catch (SQLException e) {
            logException(e);
            return null;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
    }

    public List<Word> getAll() {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = getReadableDatabase();
            List<Word> words = new ArrayList<>();

            cursor = db.query(TABLE_WORDS, new String[]{WORD_ID, WORD}, null, null, null, null, WORD);
            while (cursor.moveToNext()) {
                words.add(getWord(cursor));
            }
            return words;
        } catch (SQLException e) {
            logException(e);
            return new ArrayList<>();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
    }

    public boolean addWord(Word word) {
        SQLiteDatabase db = null;
        try {
            if (word.getWord() == null || word.getWord().trim().isEmpty()) return false;
            word.setWord(word.getWord().trim().toUpperCase());
            db = getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(WORD, word.getWord());
            db.insertOrThrow(TABLE_WORDS, null, cv);
            return true;
        } catch (SQLException e) {
            logException(e);
            return false;
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    public void deleteWord(Word word) {
        SQLiteDatabase db = null;
        try {
            db = getWritableDatabase();
            db.delete(TABLE_WORDS, "_id = ?", new String[]{Integer.toString(word.getId())});
        } catch (SQLException e) {
            logException(e);
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    @NonNull
    private Word getWord(Cursor cursor) {
        return new Word(
                cursor.getInt(cursor.getColumnIndex(WORD_ID)),
                cursor.getString(cursor.getColumnIndex(WORD))
        );
    }

    private void logException(SQLException e) {
        Log.e("SQLError", e.getMessage());
    }
}
