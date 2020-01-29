/**
 * Enum to store all of the cities in the game.
 *
 * Each city has a:
 * * Country
 * * x coordinate
 * * y coordinate
 *
 * @author Josh Dratler
 * @author Hamza Memon
 * @author Patrick Milano
 * @author Jhoan Esteban Osorno
 * @author Brian Smith
 * @version (1.0)
 */
public enum City {
    
    // Scotland
    STORNOWAY("S", 372, 39),
    WICK("S", 517, 82),
    ULLAPOOL("S", 405, 81),
    INVERNESS("S", 433, 125),
    FORT_WILLIAM("S", 361, 168),
    ABERDEEN("S", 509, 202),
    DUNDEE("S", 456, 234),
    GLASGOW("S", 364, 261),
    EDINBURGH("S", 420, 277),
    STRANRAER("S", 301, 315),
    
    // Ireland and France
    SLIGO("I", 116, 277),
    LONDONDERRY("I", 207, 245),
    DUBLIN("I", 177, 407),
    DUNDALK("I", 144, 354),
    ROSSLARE("I", 131, 483),
    CORK("I", 40, 470),
    LIMERICK("I", 50, 405),
    GALWAY("I", 50, 341),
    BELFAST("I", 246, 321),
    TULLAMORE("I", 122, 392),
    FRANCE1("I", 541, 788),
    FRANCE2("I", 281, 820),
    
    // Wales
    HOLYHEAD("W", 257, 449),
    ABERYSTWYTH("W", 237, 531),
    LLANDRINDOD_WELLS("W", 275, 568),
    CARMARTHEN("W", 204, 577),
    CARDIFF("W", 244, 617),
    
    // England
    CARLISLE("E", 386, 367),
    BARROW("E", 349, 412),
    NEWCASTLE("E", 452, 383),
    LEEDS("E", 418, 466),
    HULL("E", 466, 501),
    LIVERPOOL("E", 324, 461),
    MANCHESTER("E", 369, 491),
    NOTTINGHAM("E", 401, 555),
    BIRMINGHAM("E", 351, 580),
    NORTHAMPTON("E", 389, 622),
    BRISTOL("E", 280, 651),
    PLYMOUTH("E", 167, 677),
    PENZANCE("E", 81, 669),
    READING("E", 357, 670),
    LONDON("E", 411, 689),
    CAMBRIDGE("E", 441, 638),
    NORWICH("E", 525, 633),
    IPSWICH("E", 483, 683),
    DOVER("E", 474, 750),
    BRIGHTON("E", 385, 749),
    SOUTHAMPTON("E", 327, 724),
    
    //New York
    NEWYORK("E", 30, 800);
    
    private String country;
    private int x;
    private int y;
    
    /**
     * Constructs a city enum.
     *
     * @param country the name of the country the city is in
     * @param x,      the x coordinate of the city
     * @param y,      the y coordinate of the city
     */
    City(String country, int x, int y) {
        this.country = country;
        this.x = x;
        this.y = y;
    }
    
    /**
     * Retrieves the name of the country the city is in.
     *
     * @return the name of the country the city is in
     */
    public String getCountry() {
        return country;
    }
    
    /**
     * Retrieves the x coordinate of the city.
     *
     * @return the x coordinate
     */
    public int getX() {
        return x;
    }
    
    /**
     * Retrieves the y coordinate of the city.
     *
     * @return the y coordinate
     */
    public int getY() {
        return y;
    }
}
