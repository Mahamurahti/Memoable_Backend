package com.memoable.backend.model;

import org.bson.types.ObjectId;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class representing an Note from the database
 */
public class Note {

    /**
     * Id of the note
     */
    private ObjectId id;

    /**
     * Id of the user that created the note
     */
    private String userId;

    /**
     * Title of the note
     */
    private String title;

    /**
     * Content of the note
     */
    private String content;

    /**
     * Date the note was last modified on
     */
    private Date date;

    /**
     * Tag of the note
     */
    private Tag tag;

    /**
     * Label of the note
     */
    private String label;

    /**
     * Default constructor
     * Creates the note's id and initializes other variables as empty
     */
    public Note() {
        this.id = new ObjectId();
        this.title = null;
        this.userId = null;
        this.content = null;
        this.date = new Date();
        this.tag = Tag.NONE;
        this.label = "";
    }

    /**
     * Constructor with title and content
     * Creates the note's id and initializes other variables as empty
     * @param title title of the note
     * @param content content of the note
     */
    public Note(String title, String content) {
        this.id = new ObjectId();
        this.title = title;
        this.userId = null;
        this.content = content;
        this.date = new Date();
        this.tag = Tag.NONE;
        this.label = "";
    }

    /**
     * Returns note's id
     * @return id of the note
     */
    public String getNoteId() {
        return this.id.toString();
    }

    /**
     * Sets the user id of the note
     * @param userId id to be set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Sets the id of the note
     * @param id ObjectId to be set
     */
    public void setId(ObjectId id) {
        this.id = id;
    }

    /**
     * Sets the id of the note
     * @param id String id to be set
     */
    public void setId(String id) {
        this.id = new ObjectId(id);
    }

    /**
     * Returns the notes conent
     * @return content of the note
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the content of the note
     * @param content content to be set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Sets the date of the note
     * @param date date to be set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Returns the date of the Note in a formatted state
     * @return date formatted to String
     * @throws ParseException if date cannot be parsed
     */
    public String getDateString() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd.MM.yyyy");
        return sdf.format(this.date);
    }

    /**
     * Returns the title of the note
     * @return title of the note
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Sets the title of the note
     * @param title title to be set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the tag of the note
     * @return tag set to the note
     */
    public Tag getTag() {
        return this.tag;
    }

    /**
     * Sets the tag of the note
     * @param tag tag to be set
     */
    public void setTag(Tag tag) {
        this.tag = tag;
    }

    /**
     * Returns the label of the note
     * @return label of the note
     */
    public String getLabel() {
        return this.label;
    }

    /**
     * Sets the label of the note
     * @param label label to be set
     */
    public void setLabel(String label) {
        this.label = label;
    }
}
