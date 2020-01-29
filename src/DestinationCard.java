import javax.swing.ImageIcon;

/**
 * Enum to store the destination cards.
 *
 * Each Destination Card has a:
 * * Start city
 * * End city
 * * Point value.
 *
 * @author Josh Dratler
 * @author Hamza Memon
 * @author Patrick Milano
 * @author Jhoan Esteban Osorno
 * @author Brian Smith
 * @version (1.0)
 */
public enum DestinationCard {
    
    ABERDEEN_GLASGOW(City.ABERDEEN, City.GLASGOW, 5),
    ABERYSTWYTH_CARDIFF(City.ABERYSTWYTH, City.CARDIFF, 2),
    BELFAST_DUBLIN(City.BELFAST, City.DUBLIN, 4),
    BELFAST_MANCHESTER(City.BELFAST, City.MANCHESTER, 9),
    BIRMINGHAM_CAMBRIDGE(City.BIRMINGHAM, City.CAMBRIDGE, 2),
    BIRMINGHAM_LONDON(City.BIRMINGHAM, City.LONDON, 4),
    BRISTOL_SOUTHAMPTON(City.BRISTOL, City.SOUTHAMPTON, 2),
    CAMBRIDGE_LONDON(City.CAMBRIDGE, City.LONDON, 3),
    CARDIFF_LONDON(City.CARDIFF, City.LONDON, 8),
    CARDIFF_READING(City.CARDIFF, City.READING, 4),
    CORK_LEEDS(City.CORK, City.LEEDS, 13),
    DUBLIN_CORK(City.DUBLIN, City.CORK, 6),
    DUBLIN_LONDON(City.DUBLIN, City.LONDON, 15),
    DUNDALK_CARLISLE(City.DUNDALK, City.CARLISLE, 7),
    EDINBURGH_BIRMINGHAM(City.EDINBURGH, City.BIRMINGHAM, 12),
    EDINBURGH_LONDON(City.EDINBURGH, City.LONDON, 15),
    FORTWILLIAM_EDINBURGH(City.FORT_WILLIAM, City.EDINBURGH, 3),
    GALWAY_BARROW(City.GALWAY, City.BARROW, 12),
    GALWAY_DUBLIN(City.GALWAY, City.DUBLIN, 5),
    GLASGOW_DUBLIN(City.GLASGOW, City.DUBLIN, 9),
    GLASGOW_FRANCE1(City.GLASGOW, City.FRANCE1, 19),
    GLASGOW_MANCHESTER(City.GLASGOW, City.MANCHESTER, 11),
    HOLYHEAD_CARDIFF(City.HOLYHEAD, City.CARDIFF, 4),
    INVERNESS_BELFAST(City.INVERNESS, City.BELFAST, 10),
    INVERNESS_LEEDS(City.INVERNESS, City.LEEDS, 13),
    LEEDS_FRANCE1(City.LEEDS, City.FRANCE1, 10),
    LEEDS_LONDON(City.LEEDS, City.LONDON, 6),
    LEEDS_MANCHESTER(City.LEEDS, City.MANCHESTER, 1),
    LIMERICK_CARDIFF(City.LIMERICK, City.CARDIFF, 12),
    LIVERPOOL_HULL(City.LIVERPOOL, City.HULL, 3),
    LIVERPOOL_LLANDRINDOD(City.LIVERPOOL, City.LLANDRINDOD_WELLS, 6),
    LIVERPOOL_SOUTHAMPTON(City.LIVERPOOL, City.SOUTHAMPTON, 6),
    LONDON_BRIGHTON(City.LONDON, City.BRIGHTON, 3),
    LONDON_FRANCE1(City.LONDON, City.FRANCE1, 7),
    LONDONDERRY_BIRMINGHAM(City.LONDONDERRY, City.BIRMINGHAM, 15),
    LONDONDERRY_DUBLIN(City.LONDONDERRY, City.DUBLIN, 6),
    LONDONDERRY_STRANRAER(City.LONDONDERRY, City.STRANRAER, 4),
    MANCHESTER_LONDON(City.MANCHESTER, City.LONDON, 6),
    MANCHESTER_NORWICH(City.MANCHESTER, City.NORWICH, 6),
    MANCHESTER_PLYMOUTH(City.MANCHESTER, City.PLYMOUTH, 8),
    NEWCASTLE_HULL(City.NEWCASTLE, City.HULL, 3),
    NEWCASTLE_SOUTHAMPTON(City.NEWCASTLE, City.SOUTHAMPTON, 7),
    NORTHAMPTON_DOVER(City.NORTHAMPTON, City.DOVER, 3),
    NORWICH_IPSWICH(City.NORWICH, City.IPSWICH, 1),
    NOTTINGHAM_IPSWICH(City.NOTTINGHAM, City.IPSWICH, 3),
    PENZANCE_LONDON(City.PENZANCE, City.LONDON, 10),
    PLYMOUTH_READING(City.PLYMOUTH, City.READING, 5),
    ROSSLARE_ABERYSTWYTH(City.ROSSLARE, City.ABERYSTWYTH, 4),
    ROSSLARE_CARMARTHEN(City.ROSSLARE, City.CARMARTHEN, 6),
    SLIGO_HOLYHEAD(City.SLIGO, City.HOLYHEAD, 9),
    SOUTHAMPTON_LONDON(City.SOUTHAMPTON, City.LONDON, 4),
    STORNOWAY_ABERDEEN(City.STORNOWAY, City.ABERDEEN, 5),
    STORNOWAY_GLASGOW(City.STORNOWAY, City.GLASGOW, 7),
    STRANRAER_TULLAMORE(City.STRANRAER, City.TULLAMORE, 6),
    ULLAPOOL_DUNDEE(City.ULLAPOOL, City.DUNDEE, 4),
    WICK_DUNDEE(City.WICK, City.DUNDEE, 4),
    WICK_EDINBURGH(City.WICK, City.EDINBURGH, 5);
    
    private ImageIcon icon;
    private City start;
    private City end;
    private int value;
    private City extraCity;
    
    /**
     * Constructs a Destination Card.
     *
     * @param start starting city
     * @param end   ending city
     * @param value the value of the card.
     */
    DestinationCard(City start, City end, int value) {
        String s = name().toLowerCase();
        icon = ResourceLoader.loadImage("destinations/" + s + ".jpg");
        
        this.start = start;
        this.end = end;
        this.value = value;
        
        // Some destinations cards go to two different Frances
        if(end == City.FRANCE1) {
            extraCity = City.FRANCE2;
        }
        else {
            extraCity = null;
        }
    }
    
    /**
     * Retrieves the image icon of the card.
     *
     * @return the image of the destination card.
     */
    public ImageIcon getIcon() {
        return icon;
    }
    
    /**
     * Retrieves the start city of the card
     *
     * @return the start city of the card.
     */
    public City getStart() {
        return start;
    }
    
    /**
     * Retrieves the end city of the card
     *
     * @return the end city of the card.
     */
    public City getEnd() {
        return end;
    }
    
    /**
     * Retrieves the value of the card
     *
     * @return the value of the card.
     */
    public int getValue() {
        return value;
    }
}
