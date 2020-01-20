import java.awt.*;

/**
 * Enum to store all of th routes in the game. Each route has a country, 
 * a start city, an end city, an x array of coordinates, y array of coordinates,
 * color, length, and number of locomotives required to use the route.
 * 
 * @author Josh Dratler
 * @author Hamza Memon
 * @author Patrick Milano
 * @author Jhoan Esteban Osorno
 * @author Brian Smith
 * @version (1.0)
 */

public enum Route {

    // England routes
    SOUTHAMPTON_BRIGHTON("E", City.SOUTHAMPTON, City.BRIGHTON, new int[]{371, 368, 343, 344}, new int[]{740, 749, 741, 733}, Color.BLUE, 1, 0),
    BRIGHTON_DOVER("E", City.BRIGHTON, City.DOVER, new int[]{401, 401, 457, 459}, new int[]{754, 744, 744, 754}, Color.PINK, 2, 0),
    LONDON_BRIGHTON("E", City.LONDON, City.BRIGHTON, new int[]{391, 400, 413, 404}, new int[]{733, 738, 713, 709}, Color.GRAY, 1, 0),
    LONDON_DOVER("E", City.LONDON, City.DOVER, new int[]{417, 424, 467, 460}, new int[]{702, 697, 734, 742}, Color.GRAY, 2, 0),
    SOUTHAMPTON_LONDON_1("E", City.SOUTHAMPTON, City.LONDON, new int[]{398, 394, 342, 345}, new int[]{709, 700, 721, 729}, Color.BLACK, 2, 0),
    SOUTHAMPTON_LONDON_2("E", City.SOUTHAMPTON, City.LONDON, new int[]{341, 337, 391, 394}, new int[]{719, 711, 689, 699}, Color.RED, 2, 0),
    SOUTHAMPTON_READING("E", City.SOUTHAMPTON, City.READING, new int[]{335, 327, 344, 352}, new int[]{709, 703, 681, 686}, Color.ORANGE, 1, 0),
    BRISTOL_SOUTHAMPTON("E", City.BRISTOL, City.SOUTHAMPTON, new int[]{320, 311, 283, 291}, new int[]{707, 712, 662, 657}, Color.GREEN, 2, 0),
    LONDON_READING("E", City.LONDON, City.READING, new int[]{395, 397, 371, 367}, new int[]{688, 678, 669, 677}, Color.GREEN, 1, 0),
    BRISTOL_READING("E", City.BRISTOL, City.READING, new int[]{348, 345, 290, 293}, new int[]{663, 671, 652, 644}, Color.WHITE, 2, 0),
    READING_NORTHAMPTON("E", City.READING, City.NORTHAMPTON, new int[]{368, 360, 373, 381}, new int[]{662, 657, 633, 638}, Color.RED, 1, 0),
    LONDON_NORTHAMPTON_1("E", City.LONDON, City.NORTHAMPTON, new int[]{392, 402, 394, 385}, new int[]{666, 664, 635, 638}, Color.PINK, 1, 0),
    LONDON_NORTHAMPTON_2("E", City.LONDON, City.NORTHAMPTON, new int[]{413, 403, 396, 405}, new int[]{660, 662, 635, 634}, Color.BLUE, 1, 0),
    LONDON_CAMBRIDGE_1("E", City.LONDON, City.CAMBRIDGE, new int[]{433, 424, 435, 445}, new int[]{680, 675, 651, 654}, Color.YELLOW, 1, 0),
    LONDON_CAMBRIDGE_2("E", City.LONDON, City.CAMBRIDGE, new int[]{414, 423, 434, 426}, new int[]{670, 674, 650, 646}, Color.ORANGE, 1, 0),
    LONDON_IPSWICH("E", City.LONDON, City.IPSWICH, new int[]{464, 463, 435, 436}, new int[]{689, 680, 684, 692}, Color.WHITE, 1, 0),
    IPSWICH_NORWICH("E", City.IPSWICH, City.NORWICH, new int[]{500, 493, 508, 516}, new int[]{674, 667, 645, 650}, Color.GREEN, 1, 0),
    IPSWICH_CAMBRIDGE("E", City.IPSWICH, City.CAMBRIDGE, new int[]{470, 476, 455, 450}, new int[]{673, 666, 648, 654}, Color.BLACK, 1, 0),
    BIRMINGHAM_NORTHAMPTON_1("E", City.BIRMINGHAM, City.NORTHAMPTON, new int[]{371, 377, 358, 353}, new int[]{618, 611, 593, 599}, Color.WHITE, 1, 0),
    BIRMINGHAM_NORTHAMPTON_2("E", City.BIRMINGHAM, City.NORTHAMPTON, new int[]{379, 385, 366, 359}, new int[]{610, 605, 585, 592}, Color.GREEN, 1, 0),
    NORTHAMPTON_NOTTINGHAM("E", City.NORTHAMPTON, City.NOTTINGHAM, new int[]{400, 391, 394, 404}, new int[]{601, 599, 573, 573}, Color.ORANGE, 1, 0),
    BIRMINGHAM_NOTTINGHAM("E", City.BIRMINGHAM, City.NOTTINGHAM, new int[]{366, 362, 387, 391}, new int[]{574, 567, 555, 562}, Color.GRAY, 1, 0),
    BRISTOL_PLYMOUTH("E", City.BRISTOL, City.PLYMOUTH, new int[]{270, 268, 183, 185}, new int[]{663, 654, 672, 682}, Color.YELLOW, 3, 0),
    CAMBRIDGE_NOTTINGHAM("E", City.CAMBRIDGE, City.NOTTINGHAM, new int[]{433, 441, 417, 409}, new int[]{625, 621, 571, 575}, Color.GRAY, 2, 0),
    NOTTINGHAM_HULL("E", City.NOTTINGHAM, City.HULL, new int[]{410, 416, 460, 454}, new int[]{542, 549, 514, 507}, Color.BLACK, 2, 0),
    NOTTINGHAM_NORWICH("E", City.NOTTINGHAM, City.NORWICH, new int[]{510, 515, 419, 413}, new int[]{626, 619, 559, 565}, Color.WHITE, 4, 0),
    CAMBRIDGE_NORTHAMPTON("E", City.CAMBRIDGE, City.NORTHAMPTON, new int[]{430, 432, 405, 402}, new int[]{636, 627, 617, 625}, Color.GRAY, 1, 0),
    LIVERPOOL_BARROW("E", City.LIVERPOOL, City.BARROW, new int[]{337, 327, 340, 349}, new int[]{452, 448, 422, 426}, Color.GRAY, 1, 1),
    LEEDS_NOTTINGHAM("E", City.LEEDS, City.NOTTINGHAM, new int[]{408, 399, 410, 420}, new int[]{539, 537, 482, 483}, Color.PINK, 2, 0),
    LEEDS_HULL("E", City.LEEDS, City.HULL, new int[]{452, 457, 433, 429}, new int[]{494, 486, 472, 478}, Color.YELLOW, 1, 0),
    LEEDS_MANCHESTER_1("E", City.LEEDS, City.MANCHESTER, new int[]{386, 381, 404, 409}, new int[]{495, 488, 473, 480}, Color.BLUE, 1, 0),
    LEEDS_MANCHESTER_2("E", City.LEEDS, City.MANCHESTER, new int[]{403, 398, 375, 380}, new int[]{471, 463, 478, 485}, Color.RED, 1, 0),
    LIVERPOOL_MANCHESTER_1("E", City.LIVERPOOL, City.MANCHESTER, new int[]{352, 357, 332, 328}, new int[]{491, 484, 470, 477}, Color.PINK, 1, 0),
    LIVERPOOL_MANCHESTER_2("E", City.LIVERPOOL, City.MANCHESTER, new int[]{334, 338, 363, 358}, new int[]{468, 460, 474, 482}, Color.ORANGE, 1, 0),
    LIVERPOOL_LEEDS("E", City.LIVERPOOL, City.LEEDS, new int[]{340, 340, 400, 400}, new int[]{460, 450, 450, 460}, Color.BLACK, 2, 0),
    BIRMINGHAM_MANCHESTER_1("E", City.BIRMINGHAM, City.MANCHESTER, new int[]{356, 365, 378, 368}, new int[]{560, 562, 506, 505}, Color.YELLOW, 2, 0),
    BIRMINGHAM_MANCHESTER_2("E", City.BIRMINGHAM, City.MANCHESTER, new int[]{367, 358, 345, 354}, new int[]{504, 503, 557, 559}, Color.BLACK, 2, 0),
    NEWCASTLE_LEEDS_1("E", City.NEWCASTLE, City.LEEDS, new int[]{427, 436, 456, 447}, new int[]{451, 454, 402, 399}, Color.ORANGE, 2, 0),
    NEWCASTLE_LEEDS_2("E", City.NEWCASTLE, City.LEEDS, new int[]{445, 425, 416, 437}, new int[]{398, 450, 447, 396}, Color.WHITE, 2, 0),
    CARLISLE_NEWCASTLE("E", City.CARLISLE, City.NEWCASTLE, new int[]{429, 432, 405, 402}, new int[]{383, 374, 366, 374}, Color.YELLOW, 1, 0),
    BARROW_CARLISLE("E", City.BARROW, City.CARLISLE, new int[]{365, 357, 373, 380}, new int[]{403, 398, 375, 380}, Color.RED, 1, 0),
    SOUTHAMPTON_NEWYORK("E", City.SOUTHAMPTON, City.NEWYORK, new int[]{33, 59, 63, 88, 91, 118, 123, 148, 152, 178, 181, 207, 211, 236, 240, 265, 268, 291, 294, 316, 309, 289, 286, 263, 261, 236,
        234, 208, 206, 179, 177, 151, 147, 122, 118, 92, 90, 63, 61, 37, 33,}, new int[]{779, 788, 788, 794, 794, 795, 794, 792, 792, 789, 788, 784, 782, 775, 774, 764, 762, 750, 748, 735, 728,
        740, 741, 754, 756, 766, 767, 774, 774, 779, 779, 783, 783, 785, 785, 784, 784, 779, 778, 771, 779}, Color.GRAY, 10, 3),
    SOUTHAMPTON_PLYMOUTH("E", City.SOUTHAMPTON, City.PLYMOUTH, new int[]{175, 197, 199, 225, 226, 252, 255, 281, 283, 309, 309, 284, 281, 253, 250, 225, 221, 196, 193, 171
        , 175}, new int[]{686, 697, 697, 706, 707, 713, 714, 718, 717, 718, 726, 726, 725, 722, 721, 715, 714, 705, 704, 693, 686}, Color.GRAY, 5, 1),
    CAMBRIDGE_NORWICH("E", City.CAMBRIDGE, City.NORWICH, new int[]{482, 482, 453, 454, 484, 487, 515, 510},
    new int[]{638, 647, 643, 634, 639, 647, 638, 630}, Color.RED, 2, 0),
    PLYMOUTH_PENZANCE_BLACK("E", City.PLYMOUTH, City.PENZANCE, new int[]{95, 96, 153, 152},
    new int[]{675, 666, 674, 683}, Color.BLACK, 2, 0),
    PLYMOUTH_PENZANCE_GRAY("E", City.PLYMOUTH, City.PENZANCE, new int[]{153, 135, 132, 105, 103, 92, 86, 96, 105, 131, 142, 158, 153},
    new int[]{691, 707, 708, 707, 705, 684, 689, 710, 715, 717, 714, 699, 691}, Color.GRAY, 3, 1),
    HULL_NORWICH("E", City.HULL, City.NORWICH, new int[]{515, 491, 489, 469, 466, 460, 459, 461, 469, 468, 468, 475, 477, 494, 497, 519},
    new int[]{618, 602, 601, 580, 576, 549, 545, 518, 519, 545, 547, 577, 576, 596, 597, 610}, Color.GRAY, 4, 0),
    BARROW_LEEDS("E", City.BARROW, City.LEEDS, new int[]{388, 394, 411, 404, 388, 386, 390, 364, 361, 386},
    new int[]{431, 425, 449, 454, 432, 430, 422, 411, 420, 430}, Color.GREEN, 2, 0),
    HULL_NEWCASTLE("E", City.HULL, City.NEWCASTLE, new int[]{467, 490, 491, 506, 507, 510, 511, 500, 499, 480, 485, 504, 507, 519, 519, 516, 514, 498, 492, 472, 467},
    new int[]{392, 402, 404, 425, 428, 453, 456, 481, 483, 497, 503, 491, 485, 461, 454, 427, 421, 399, 394, 386, 392}, Color.GRAY, 5, 1),
    BIRMINGHAM_READING("E", City.BIRMINGHAM, City.READING, new int[]{349, 351, 341, 339, 349, 349, 357, 348, 340, 349}, new int[]{628, 600, 600, 627, 628, 631, 656, 659, 632, 631}, Color.GRAY, 2, 0),

    // Ireland and France Routes
    DOVER_FRANCE("I", City.DOVER, City.FRANCE2, new int[]{536, 531, 481, 487}, new int[]{782, 790, 758, 751}, Color.GRAY, 2, 1),
    CORK_LIMERICK("I", City.CORK, City.LIMERICK, new int[]{39, 48, 55, 45}, new int[]{448, 450, 422, 421}, Color.PINK, 1, 0),
    CORK_ROSSLARE("I", City.CORK, City.ROSSLARE, new int[]{57, 58, 115, 115}, new int[]{474, 465, 474, 484}, Color.BLUE, 2, 0),
    LIMERICK_GALWAY("I", City.LIMERICK, City.GALWAY, new int[]{46, 56, 56, 46}, new int[]{388, 388, 358, 358}, Color.YELLOW, 1, 0),
    GALWAY_TULLAMORE_1("I", City.GALWAY, City.TULLAMORE, new int[]{71, 116, 111, 66}, new int[]{344, 378, 385, 350}, Color.GRAY, 2, 0),
    GALWAY_TULLAMORE_2("I", City.GALWAY, City.TULLAMORE, new int[]{65, 110, 104, 59}, new int[]{351, 386, 393, 359}, Color.GRAY, 2, 0),
    SOUTHAMPTON_FRANCE_1("I", City.SOUTHAMPTON, City.FRANCE1, new int[]{275, 294, 295, 309, 310, 319, 328, 318, 316, 303, 300, 284, 275},
    new int[]{806, 787, 783, 761, 758, 734, 737, 762, 765, 788, 791, 812, 806}, Color.GRAY, 3, 1),
    SOUTHAMPTON_FRANCE_2("I", City.SOUTHAMPTON, City.FRANCE2, new int[]{286, 294, 311, 311, 325, 327, 337, 329, 318, 318, 303, 302, 286},
    new int[]{812, 818, 797, 793, 770, 767, 745, 741, 763, 766, 788, 792, 812}, Color.GRAY, 3, 1),
    LIMERICK_TULLAMORE("I", City.LIMERICK, City.TULLAMORE, new int[]{71, 70, 98, 99}, new int[]{408, 398, 394, 403}, Color.GRAY, 1, 0),
    CORK_TULLAMORE("I", City.CORK, City.TULLAMORE, new int[]{112, 119, 55, 48}, new int[]{398, 404, 463, 456}, Color.YELLOW, 3, 0),
    TULLAMORE_DUBLIN_1("I", City.TULLAMORE, City.DUBLIN, new int[]{139, 138, 164, 167}, new int[]{386, 395, 400, 393}, Color.GREEN, 1, 0),
    TULLAMORE_DUBLIN_2("I", City.TULLAMORE, City.DUBLIN, new int[]{165, 163, 136, 137}, new int[]{403, 412, 406, 397}, Color.ORANGE, 1, 0),
    ROSSLARE_DUBLIN_1("I", City.ROSSLARE, City.DUBLIN, new int[]{133, 162, 171, 141}, new int[]{464, 416, 421, 468}, Color.WHITE, 2, 0),
    ROSSLARE_DUBLIN_2("I", City.ROSSLARE, City.DUBLIN, new int[]{142, 151, 180, 172}, new int[]{469, 475, 426, 422}, Color.BLACK, 2, 0),
    GALWAY_SLIGO("I", City.GALWAY, City.SLIGO, new int[]{65, 58, 100, 107}, new int[]{330, 324, 284, 291}, Color.ORANGE, 2, 0),
    SLIGO_TULLAMORE("I", City.SLIGO, City.TULLAMORE, new int[]{113, 123, 126, 118}, new int[]{291, 290, 376, 376}, Color.BLUE, 3, 0),
    SLIGO_DUNDALK("I", City.SLIGO, City.DUNDALK, new int[]{185, 191, 130, 124}, new int[]{349, 342, 281, 288}, Color.BLACK, 3, 0),
    SLIGO_LONDONDERRY("I", City.SLIGO, City.LONDONDERRY, new int[]{136, 132, 185, 189}, new int[]{273, 264, 243, 252}, Color.GREEN, 2, 0),
    DUBLIN_DUNDALK_1("I", City.DUBLIN, City.DUNDALK, new int[]{173, 182, 191, 182}, new int[]{390, 364, 367, 393}, Color.YELLOW, 1, 0),
    DUBLIN_DUNDALK_2("I", City.DUBLIN, City.DUNDALK, new int[]{183, 192, 201, 192}, new int[]{393, 368, 370, 396}, Color.BLUE, 1, 0),
    DUNDALK_BELFAST_1("I", City.DUNDALK, City.BELFAST, new int[]{206, 213, 233, 227}, new int[]{341, 347, 329, 322}, Color.WHITE, 1, 0),
    DUNDALK_BELFAST_2("I", City.DUNDALK, City.BELFAST, new int[]{214, 233, 240, 220}, new int[]{348, 330, 337, 355}, Color.RED, 1, 0),
    BELFAST_LONDONDERRY("I", City.BELFAST, City.LONDONDERRY, new int[]{235, 243, 216, 208}, new int[]{312, 308, 259, 262}, Color.ORANGE, 2, 0),
    TULLAMORE_ROSSLARE("I", City.TULLAMORE, City.ROSSLARE, new int[]{119, 128, 127, 127, 130, 121, 118, 117, 119},
    new int[]{409, 408, 436, 439, 467, 467, 440, 436, 409}, Color.RED, 2, 0),
    LONDONDERRY_DUNDALK("I", City.LONDONDERRY, City.DUNDALK, new int[]{196, 188, 187, 185, 186, 194, 202, 193, 194, 196, 196, 204, 196},
    new int[]{256, 282, 285, 313, 318, 344, 342, 317, 313, 287, 284, 260, 256}, Color.PINK, 3, 0),
    BELFAST_BARROW("I", City.BELFAST, City.BARROW, new int[]{338, 345, 258, 250}, new int[]{408, 400, 326, 332}, Color.GRAY, 4, 1),
    CORK_PENZANCE("I", City.CORK, City.PENZANCE, new int[]{37, 47, 85, 75}, new int[]{486, 484, 654, 655}, Color.GRAY, 6, 2),

    // Scotland Routes
    WICK_STORNOWAY("S", City.WICK, City.STORNOWAY, new int[]{510, 520, 511, 502, 501, 501, 384, 385}, new int[]{39, 67, 69, 43, 43, 33, 33, 43}, Color.GRAY, 5, 2),
    WICK_INVERNESS("S", City.WICK, City.INVERNESS, new int[]{450, 445, 495, 500}, new int[]{119, 110, 83, 91}, Color.RED, 2, 0),
    ULLAPOOL_STORNOWAY("S", City.ULLAPOOL, City.STORNOWAY, new int[]{401, 385, 377, 393}, new int[]{70, 46, 49, 73}, Color.GRAY, 1, 1),
    INVERNESS_ULLAPPOL("S", City.INVERNESS, City.ULLAPOOL, new int[]{424, 432, 419, 411}, new int[]{117, 113, 89, 93}, Color.ORANGE, 1, 0),
    DUNDEE_INVERNESS("S", City.DUNDEE, City.INVERNESS, new int[]{450, 459, 441, 431}, new int[]{220, 220, 136, 137}, Color.BLUE, 3, 0),
    DUNDEE_ABERDEEN("S", City.DUNDEE, City.ABERDEEN, new int[]{465, 471, 495, 489}, new int[]{221, 229, 214, 206}, Color.WHITE, 1, 0),
    NEWCASTLE_EDINBURGH_1("S", City.NEWCASTLE, City.EDINBURGH, new int[]{438, 415, 424, 448}, new int[]{374, 289, 287, 369}, Color.GREEN, 3, 0),
    NEWCASTLE_EDINBURGH_2("S", City.NEWCASTLE, City.EDINBURGH, new int[]{450, 458, 435, 426}, new int[]{368, 366, 284, 287}, Color.PINK, 3, 0),
    CARLISLE_EDINBURGH("S", City.CARLISLE, City.EDINBURGH, new int[]{397, 387, 404, 413}, new int[]{352, 349, 295, 297}, Color.ORANGE, 2, 0),
    EDINBURGH_STRANRAER("S", City.EDINBURGH, City.STRANRAER, new int[]{405, 403, 318, 322}, new int[]{292, 283, 309, 317}, Color.WHITE, 3, 0),
    STRANRAER_GLASGOW("S", City.STRANRAER, City.GLASGOW, new int[]{359, 352, 308, 314}, new int[]{275, 267, 303, 309}, Color.RED, 2, 0),
    GLASGOW_EDINBURGH_1("S", City.GLASGOW, City.EDINBURGH, new int[]{404, 406, 378, 376}, new int[]{280, 270, 261, 272}, Color.BLACK, 1, 0),
    GLASGOW_EDINBURGH_2("S", City.GLASGOW, City.EDINBURGH, new int[]{407, 409, 382, 379}, new int[]{270, 261, 253, 262}, Color.BLUE, 1, 0),
    FORT_WILLIAM_INVERNESS("S", City.FORT_WILLIAM, City.INVERNESS, new int[]{377, 372, 420, 426}, new int[]{165, 157, 128, 135}, Color.BLACK, 2, 0),
    FORT_WILLIAM_GLASGOW("S", City.FORT_WILLIAM, City.GLASGOW, new int[]{370, 360, 362, 372}, new int[]{188, 188, 245, 245}, Color.ORANGE, 2, 0),
    FORT_WILLIAM_ULLAPOOL("S", City.FORT_WILLIAM, City.ULLAPOOL, new int[]{373, 364, 389, 397}, new int[]{148, 144, 93, 96}, Color.PINK, 2, 0),
    FORT_WILLIAM_DUNDEE("S", City.FORT_WILLIAM, City.DUNDEE, new int[]{441, 446, 377, 372}, new int[]{230, 222, 172, 179}, Color.GREEN, 3, 0),
    EDINBURGH_DUNDEE_YELLOW("S", City.EDINBURGH, City.DUNDEE, new int[]{420, 437, 445, 428}, new int[]{257, 236, 242, 263}, Color.YELLOW, 1, 0),
    EDINBURGH_DUNDEE_RED("S", City.EDINBURGH, City.DUNDEE, new int[]{429, 446, 453, 436}, new int[]{264, 243, 249, 269}, Color.RED, 1, 0),
    WICK_ABERDEEN("S", City.WICK, City.ABERDEEN, new int[]{519, 509, 510, 520}, new int[]{182, 182, 96, 96}, Color.GRAY, 3, 1),
    ULLAPOOL_WICK("S", City.ULLAPOOL, City.WICK, new int[]{416, 440, 447, 474, 477, 504, 503, 476, 473, 447, 445, 420},
    new int[]{75, 64, 62, 62, 62, 70, 78, 71, 71, 71, 72, 84}, Color.YELLOW, 5, 3),
    CARLISLE_STRANRAER("S", City.CARLISLE, City.STRANRAER, new int[]{300, 309, 314, 338, 345, 372, 370, 346, 342, 318, 316, 308, 300},
    new int[]{329, 353, 359, 372, 374, 370, 360, 364, 365, 352, 350, 327, 329}, Color.GRAY, 3, 1),
    EDINBURGH_ABERDEEN("S", City.EDINBURGH, City.ABERDEEN, new int[]{506, 506, 505, 495, 490, 469, 458, 435, 438, 461, 464, 484, 485, 496, 497, 496, 506},
    new int[]{214, 240, 247, 271, 276, 293, 295, 282, 276, 286, 287, 269, 267, 243, 240, 215, 214}, Color.GRAY, 4, 1),
    NEWCASTLE_ABERDEEN("S", City.NEWCASTLE, City.ABERDEEN, new int[]{507, 507, 507, 508, 508, 506, 506, 498, 497, 484, 482, 466, 472, 489, 492, 505, 507, 513, 513, 516, 516, 516, 517, 516, 507},
    new int[]{217, 240, 243, 270, 273, 298, 302, 327, 329, 352, 354, 370, 376, 361, 355, 333, 329, 303, 299, 273, 270, 244, 242, 217, 217}, Color.GRAY, 6, 1),
    ABERDEEN_INVERNESS("S", City.ABERDEEN, City.INVERNESS, new int[]{445, 471, 476, 494, 496, 505, 497, 489, 488, 470, 469, 442, 445},
    new int[]{126, 134, 138, 154, 164, 189, 192, 166, 165, 144, 143, 134, 126}, Color.PINK, 3, 0),
    FORT_WILLIAM_STORNOWAY("S", City.FORT_WILLIAM, City.STORNOWAY, new int[]{353, 333, 328, 312, 311, 310, 312, 324, 327, 344, 350, 332, 331, 318, 319, 320, 320, 334, 337, 358, 353},
    new int[]{36, 48, 55, 77, 83, 110, 117, 140, 145, 159, 153, 138, 138, 113, 110, 84, 81, 59, 56, 44, 36,}, Color.GRAY, 5, 1),

    // Wales Routes
    BIRMINGHAM_CARDIFF_1("W", City.BIRMINGHAM, City.CARDIFF, new int[]{341, 337, 258, 261}, new int[]{591, 583, 614, 623}, Color.ORANGE, 3, 0),
    BIRMINGHAM_CARDIFF_2("W", City.BIRMINGHAM, City.CARDIFF, new int[]{258, 254, 332, 336}, new int[]{612, 604, 573, 581}, Color.BLUE, 3, 0),
    BRISTOL_CARDIFF("W", City.BRISTOL, City.CARDIFF, new int[]{250, 270, 278, 256},
    new int[]{629, 645, 639, 621}, Color.GRAY, 1, 1),
    CARDIFF_CARMARTHEN("W", City.CARDIFF, City.CARMARTHEN, new int[]{232, 239, 219, 212}, new int[]{609, 600, 582, 589}, Color.RED, 1, 0),
    LLANDRINDOD_WELLS_ABERYSTWYTH("W", City.LLANDRINDOD_WELLS, City.ABERYSTWYTH, new int[]{264, 271, 250, 243}, new int[]{562, 556, 536, 543}, Color.WHITE, 1, 0),
    CARMARTHEN_ABERYSTWYTH("W", City.CARMARTHEN, City.ABERYSTWYTH, new int[]{217, 209, 225, 233}, new int[]{566, 560, 538, 544}, Color.YELLOW, 1, 0),
    PENZANCE_CARDIFF("W", City.PENZANCE, City.CARDIFF, new int[]{100, 118, 121, 146, 148, 173, 176, 202, 205, 231, 230, 204, 202, 175, 173, 146, 143, 117, 111, 92, 100},
    new int[]{661, 645, 643, 635, 634, 631, 629, 625, 625, 621, 612, 616, 616, 621, 621, 626, 627, 636, 637, 655, 661}, Color.GRAY, 5, 1),
    HOLYHEAD_LIVERPOOL("W", City.HOLYHEAD, City.LIVERPOOL, new int[]{295, 270, 266, 292, 295, 297, 315, 322, 303}, new int[]{435, 444, 435, 425, 435, 435, 454, 448, 428}, Color.GRAY, 2, 1),
    CARDIFF_LLANDRINDOD_WELLS("W", City.CARDIFF, City.LLANDRINDOD_WELLS, new int[]{251, 244, 260, 268}, new int[]{603, 597, 574, 580}, Color.PINK, 1, 0),
    HOLYHEAD_LLANDRINDOD_WELLS("W", City.HOLYHEAD, City.LLANDRINDOD_WELLS, new int[]{271, 281, 266, 257}, new int[]{549, 548, 464, 464}, Color.BLUE, 3, 0),
    HOLYHEAD_ABERYSTWYTH("W", City.HOLYHEAD, City.ABERYSTWYTH, new int[]{247, 254, 244, 237, 247, 246, 236, 237, 246, 246}, new int[]{488, 464, 461, 486, 488, 490, 490, 516, 516, 491}, Color.GRAY,
    2, 1),
    LLANDRINDOD_WELLS_BIRMINGHAM("W", City.LLANDRINDOD_WELLS, City.BIRMINGHAM, new int[]{317, 340, 345, 321, 317, 315, 312, 286, 289, 315}, new int[]{558, 572, 564, 550, 558, 558, 550, 559, 567,
        558}, Color.RED, 2, 0),
    LLANDRINDOD_WELLS_MANCHESTER("W", City.LLANDRINDOD_WELLS, City.MANCHESTER, new int[]{311, 357, 352, 306, 311, 309, 291, 285, 303, 309}, new int[]{533, 501, 494, 526, 533, 535, 554, 548, 528,
        535}, Color.GREEN, 3, 0),
    // Wales to Ireland Routes
    ROSSLARE_HOLYHEAD("WI", City.ROSSLARE, City.HOLYHEAD, new int[]{238, 235, 151, 154}, new int[]{460, 452, 475, 484}, Color.GRAY, 3, 1),
    DUBLIN_HOLYHEAD("WI", City.DUBLIN, City.HOLYHEAD, new int[]{186, 191, 241, 237}, new int[]{419, 412, 442, 450}, Color.GRAY, 2, 1),
    ROSSLARE_ABERYSTWYTH("WI", City.ROSSLARE, City.ABERYSTWYTH, new int[]{223, 226, 146, 142}, new int[]{530, 521, 488, 496}, Color.GRAY, 3, 1),
    DUNDALK_HOLYHEAD("WI", City.DUNDALK, City.HOLYHEAD, new int[]{247, 255, 211, 204}, new int[]{439, 436, 360, 364}, Color.GRAY, 3, 1),
    ROSSLARE_CARMARTHEN("WI", City.ROSSLARE, City.CARMARTHEN, new int[]{119, 120, 122, 133, 136, 154, 165, 190, 192, 166, 165, 142, 141, 130, 130, 130, 119},
    new int[]{499, 523, 528, 554, 559, 574, 576, 582, 574, 569, 567, 553, 550, 527, 523, 499, 499}, Color.GRAY, 4, 1),

    // Scotland to Ireland Routes
    LONDONDERRY_STRANRAER("SI", City.LONDONDERRY, City.STRANRAER, new int[]{290, 296, 225, 219}, new int[]{308, 301, 251, 257}, Color.GRAY, 3, 1),
    LONDONDERRY_GLASGOW("SI", City.LONDONDERRY, City.GLASGOW, new int[]{351, 351, 235, 235}, new int[]{262, 252, 243, 253}, Color.GRAY, 4, 1),
    LONDONDERRY_FORT_WILLIAM("SI", City.LONDONDERRY, City.FORT_WILLIAM, new int[]{226, 219, 349, 354}, new int[]{244, 236, 169, 177}, Color.GRAY, 5, 1),
    BELFAST_STRANRAER("SI", City.BELFAST, City.STRANRAER, new int[]{289, 260, 260, 289}, new int[]{322, 323, 313, 313}, Color.GRAY, 1, 1);
    
    private final Polygon polygon;
    private final String country;
    private final City start;
    private final City end;
    private final Color color;
    private final int length;
    private final int ferry;
    private boolean claimed;
    private int points;
    private Player player;
    private Route doubleRoute;
    /**
     * Constructor for route objects. Initializes a polygon 
     * based on the x and y coordinates, and inilializes 
     * parameters below
     * 
     * @param country country of the route (represented with an abbreviation)
     * @param start start city of the route
     * @param end end city of the route
     * @param x an array of the x coordinates of the route
     * @param y an array of the y coordinates of the route
     * @param color the color of the route
     * @param length the length of the route
     * @param ferry the number of ferry routes
     */
    Route(String country, City start, City end, int[] x, int[] y, Color color, int length, int ferry){
        polygon = new Polygon(x, y, x.length);
        this.country = country;
        this.color = color;
        this.length = length;
        this.ferry = ferry;
        this.start = start;
        this.end = end;
        this.claimed = false;
        this.player = null;
        this.doubleRoute = null;
    }

    /**
     * Method to get the name of the country of the route
     * 
     * @return the name of the country
     */
    public String getCountry(){
        return country;
    }

    /**
     * Method to get the start city of the route
     * 
     * @return the start city of the route
     */
    public City getStart(){
        return start;
    }

    /**
     * Method to get the end city of the route
     * 
     * @return the end city of the route
     */  
    public City getEnd(){
        return end;
    }

    /**
     * Method to get the color of the route
     * 
     * @return the color of the route
     */  
    public Color getColor(){
        return color;
    }

    /**
     * Method to get the number of ferrys required for that route
     * 
     * @return number of ferrys for that route
     */
    public int getFerry(){
        return ferry;
    }

    /**
     * Method to get the length of the route
     * 
     * @return length of route
     */
    public int getLength(){
        return length;
    }

    /**
     * Method to get the Polygon of the route 
     * 
     * @return the Polygon of the route
     */
    public Polygon getPolygon(){
        return polygon;
    }

    /**
     * Method to check to see if a route has been claimed
     * 
     * @return true if a route has been claimed, false if it has 
     * not been claimed.
     */    

    public boolean isClaimed(){
        return claimed;
    }

    /**
     * Method to set a route to be claimed
     */
    public void setClaim(){
        claimed = true;
    }

    /**
     * Method to set the player being looked at.
     * 
     * @param player the new player to be looked at.
     */

    public void setPlayer(Player player){
        this.player = player;
    }

    /**
     * Method to get the player we are currently looking at
     * 
     * @return the player we are looking at
     */

    public Player getPlayer(){
        return player;
    }

    /**
     * Method to determine the point value of the routes (based on 
     * their length)
     * 
     * @return the value of the route
     */

    public int getPoints(){
        if(length == 1){
            points = 1;
        }
        else if(length == 2){
            points = 2;
        }
        else if(length == 3){
            points = 4;
        }
        else if(length == 4){
            points = 7;
        }
        else if(length == 5){
            points = 10;
        }
        else if(length == 6){
            points = 15;
        }
        else if(end == City.NEWYORK){
            points = 40;
        }
        return points;
    }
    /**
     *  Method to set a double route
     *  
     *  @param doubleroute to be set
     */
    public void setDoubleRoute(Route doubleRoute)
    {
        this.doubleRoute = doubleRoute;
    }
    /**
     * Method to get the double route
     * 
     * @return the doubleroute
     */
    public Route getDoubleRoute()
    {
        return doubleRoute;
    }
    /**
     * Method to initialize doubleroutes
     */
    public static void setUpDoubleRoutes()
    {
        SOUTHAMPTON_LONDON_1.setDoubleRoute(SOUTHAMPTON_LONDON_2);
        SOUTHAMPTON_LONDON_2.setDoubleRoute(SOUTHAMPTON_LONDON_1);
        LONDON_NORTHAMPTON_2.setDoubleRoute(LONDON_NORTHAMPTON_1);
        LONDON_NORTHAMPTON_1.setDoubleRoute(LONDON_NORTHAMPTON_2);
        LONDON_CAMBRIDGE_1.setDoubleRoute(LONDON_CAMBRIDGE_2);
        LONDON_CAMBRIDGE_2.setDoubleRoute(LONDON_CAMBRIDGE_1);
        BIRMINGHAM_NORTHAMPTON_2.setDoubleRoute(BIRMINGHAM_NORTHAMPTON_1);
        BIRMINGHAM_NORTHAMPTON_1.setDoubleRoute(BIRMINGHAM_NORTHAMPTON_2);
        LEEDS_MANCHESTER_1.setDoubleRoute(LEEDS_MANCHESTER_2);
        LEEDS_MANCHESTER_2.setDoubleRoute(LEEDS_MANCHESTER_1);
        LIVERPOOL_MANCHESTER_2.setDoubleRoute(LIVERPOOL_MANCHESTER_1);
        LIVERPOOL_MANCHESTER_1.setDoubleRoute(LIVERPOOL_MANCHESTER_2);
        BIRMINGHAM_MANCHESTER_1.setDoubleRoute(BIRMINGHAM_MANCHESTER_2);
        BIRMINGHAM_MANCHESTER_2.setDoubleRoute(BIRMINGHAM_MANCHESTER_1);
        NEWCASTLE_LEEDS_1.setDoubleRoute(NEWCASTLE_LEEDS_2);
        NEWCASTLE_LEEDS_2.setDoubleRoute(NEWCASTLE_LEEDS_1);
        GALWAY_TULLAMORE_2.setDoubleRoute(GALWAY_TULLAMORE_1);
        GALWAY_TULLAMORE_1.setDoubleRoute(GALWAY_TULLAMORE_2);
        SOUTHAMPTON_FRANCE_1.setDoubleRoute(SOUTHAMPTON_FRANCE_2);
        SOUTHAMPTON_FRANCE_2.setDoubleRoute(SOUTHAMPTON_FRANCE_1);
        TULLAMORE_DUBLIN_1.setDoubleRoute(TULLAMORE_DUBLIN_2);
        TULLAMORE_DUBLIN_2.setDoubleRoute(TULLAMORE_DUBLIN_1);
        ROSSLARE_DUBLIN_1.setDoubleRoute(ROSSLARE_DUBLIN_2);
        ROSSLARE_DUBLIN_2.setDoubleRoute(ROSSLARE_DUBLIN_1);
        DUBLIN_DUNDALK_2.setDoubleRoute(DUBLIN_DUNDALK_1);
        DUBLIN_DUNDALK_1.setDoubleRoute(DUBLIN_DUNDALK_2);
        DUNDALK_BELFAST_2.setDoubleRoute(DUNDALK_BELFAST_1);
        DUNDALK_BELFAST_1.setDoubleRoute(DUNDALK_BELFAST_2);
        NEWCASTLE_EDINBURGH_2.setDoubleRoute(NEWCASTLE_EDINBURGH_1);
        NEWCASTLE_EDINBURGH_1.setDoubleRoute(NEWCASTLE_EDINBURGH_2);
        GLASGOW_EDINBURGH_1.setDoubleRoute(GLASGOW_EDINBURGH_2);
        GLASGOW_EDINBURGH_2.setDoubleRoute(GLASGOW_EDINBURGH_1);
        BIRMINGHAM_CARDIFF_1.setDoubleRoute(BIRMINGHAM_CARDIFF_2);
        BIRMINGHAM_CARDIFF_2.setDoubleRoute(BIRMINGHAM_CARDIFF_1);
    }
}