/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.crypto.falcon;

import org.bouncycastle.pqc.crypto.falcon.FPREngine;
import org.bouncycastle.pqc.crypto.falcon.FalconCodec;
import org.bouncycastle.pqc.crypto.falcon.FalconFFT;
import org.bouncycastle.pqc.crypto.falcon.FalconFPR;
import org.bouncycastle.pqc.crypto.falcon.FalconSmallPrime;
import org.bouncycastle.pqc.crypto.falcon.FalconSmallPrimeList;
import org.bouncycastle.pqc.crypto.falcon.FalconVrfy;
import org.bouncycastle.pqc.crypto.falcon.SHAKE256;

class FalconKeyGen {
    FPREngine fpr;
    FalconSmallPrimeList primes;
    FalconFFT fft;
    FalconCodec codec;
    FalconVrfy vrfy;
    private short[] REV10 = new short[]{0, 512, 256, 768, 128, 640, 384, 896, 64, 576, 320, 832, 192, 704, 448, 960, 32, 544, 288, 800, 160, 672, 416, 928, 96, 608, 352, 864, 224, 736, 480, 992, 16, 528, 272, 784, 144, 656, 400, 912, 80, 592, 336, 848, 208, 720, 464, 976, 48, 560, 304, 816, 176, 688, 432, 944, 112, 624, 368, 880, 240, 752, 496, 1008, 8, 520, 264, 776, 136, 648, 392, 904, 72, 584, 328, 840, 200, 712, 456, 968, 40, 552, 296, 808, 168, 680, 424, 936, 104, 616, 360, 872, 232, 744, 488, 1000, 24, 536, 280, 792, 152, 664, 408, 920, 88, 600, 344, 856, 216, 728, 472, 984, 56, 568, 312, 824, 184, 696, 440, 952, 120, 632, 376, 888, 248, 760, 504, 1016, 4, 516, 260, 772, 132, 644, 388, 900, 68, 580, 324, 836, 196, 708, 452, 964, 36, 548, 292, 804, 164, 676, 420, 932, 100, 612, 356, 868, 228, 740, 484, 996, 20, 532, 276, 788, 148, 660, 404, 916, 84, 596, 340, 852, 212, 724, 468, 980, 52, 564, 308, 820, 180, 692, 436, 948, 116, 628, 372, 884, 244, 756, 500, 1012, 12, 524, 268, 780, 140, 652, 396, 908, 76, 588, 332, 844, 204, 716, 460, 972, 44, 556, 300, 812, 172, 684, 428, 940, 108, 620, 364, 876, 236, 748, 492, 1004, 28, 540, 284, 796, 156, 668, 412, 924, 92, 604, 348, 860, 220, 732, 476, 988, 60, 572, 316, 828, 188, 700, 444, 956, 124, 636, 380, 892, 252, 764, 508, 1020, 2, 514, 258, 770, 130, 642, 386, 898, 66, 578, 322, 834, 194, 706, 450, 962, 34, 546, 290, 802, 162, 674, 418, 930, 98, 610, 354, 866, 226, 738, 482, 994, 18, 530, 274, 786, 146, 658, 402, 914, 82, 594, 338, 850, 210, 722, 466, 978, 50, 562, 306, 818, 178, 690, 434, 946, 114, 626, 370, 882, 242, 754, 498, 1010, 10, 522, 266, 778, 138, 650, 394, 906, 74, 586, 330, 842, 202, 714, 458, 970, 42, 554, 298, 810, 170, 682, 426, 938, 106, 618, 362, 874, 234, 746, 490, 1002, 26, 538, 282, 794, 154, 666, 410, 922, 90, 602, 346, 858, 218, 730, 474, 986, 58, 570, 314, 826, 186, 698, 442, 954, 122, 634, 378, 890, 250, 762, 506, 1018, 6, 518, 262, 774, 134, 646, 390, 902, 70, 582, 326, 838, 198, 710, 454, 966, 38, 550, 294, 806, 166, 678, 422, 934, 102, 614, 358, 870, 230, 742, 486, 998, 22, 534, 278, 790, 150, 662, 406, 918, 86, 598, 342, 854, 214, 726, 470, 982, 54, 566, 310, 822, 182, 694, 438, 950, 118, 630, 374, 886, 246, 758, 502, 1014, 14, 526, 270, 782, 142, 654, 398, 910, 78, 590, 334, 846, 206, 718, 462, 974, 46, 558, 302, 814, 174, 686, 430, 942, 110, 622, 366, 878, 238, 750, 494, 1006, 30, 542, 286, 798, 158, 670, 414, 926, 94, 606, 350, 862, 222, 734, 478, 990, 62, 574, 318, 830, 190, 702, 446, 958, 126, 638, 382, 894, 254, 766, 510, 1022, 1, 513, 257, 769, 129, 641, 385, 897, 65, 577, 321, 833, 193, 705, 449, 961, 33, 545, 289, 801, 161, 673, 417, 929, 97, 609, 353, 865, 225, 737, 481, 993, 17, 529, 273, 785, 145, 657, 401, 913, 81, 593, 337, 849, 209, 721, 465, 977, 49, 561, 305, 817, 177, 689, 433, 945, 113, 625, 369, 881, 241, 753, 497, 1009, 9, 521, 265, 777, 137, 649, 393, 905, 73, 585, 329, 841, 201, 713, 457, 969, 41, 553, 297, 809, 169, 681, 425, 937, 105, 617, 361, 873, 233, 745, 489, 1001, 25, 537, 281, 793, 153, 665, 409, 921, 89, 601, 345, 857, 217, 729, 473, 985, 57, 569, 313, 825, 185, 697, 441, 953, 121, 633, 377, 889, 249, 761, 505, 1017, 5, 517, 261, 773, 133, 645, 389, 901, 69, 581, 325, 837, 197, 709, 453, 965, 37, 549, 293, 805, 165, 677, 421, 933, 101, 613, 357, 869, 229, 741, 485, 997, 21, 533, 277, 789, 149, 661, 405, 917, 85, 597, 341, 853, 213, 725, 469, 981, 53, 565, 309, 821, 181, 693, 437, 949, 117, 629, 373, 885, 245, 757, 501, 1013, 13, 525, 269, 781, 141, 653, 397, 909, 77, 589, 333, 845, 205, 717, 461, 973, 45, 557, 301, 813, 173, 685, 429, 941, 109, 621, 365, 877, 237, 749, 493, 1005, 29, 541, 285, 797, 157, 669, 413, 925, 93, 605, 349, 861, 221, 733, 477, 989, 61, 573, 317, 829, 189, 701, 445, 957, 125, 637, 381, 893, 253, 765, 509, 1021, 3, 515, 259, 771, 131, 643, 387, 899, 67, 579, 323, 835, 195, 707, 451, 963, 35, 547, 291, 803, 163, 675, 419, 931, 99, 611, 355, 867, 227, 739, 483, 995, 19, 531, 275, 787, 147, 659, 403, 915, 83, 595, 339, 851, 211, 723, 467, 979, 51, 563, 307, 819, 179, 691, 435, 947, 115, 627, 371, 883, 243, 755, 499, 1011, 11, 523, 267, 779, 139, 651, 395, 907, 75, 587, 331, 843, 203, 715, 459, 971, 43, 555, 299, 811, 171, 683, 427, 939, 107, 619, 363, 875, 235, 747, 491, 1003, 27, 539, 283, 795, 155, 667, 411, 923, 91, 603, 347, 859, 219, 731, 475, 987, 59, 571, 315, 827, 187, 699, 443, 955, 123, 635, 379, 891, 251, 763, 507, 1019, 7, 519, 263, 775, 135, 647, 391, 903, 71, 583, 327, 839, 199, 711, 455, 967, 39, 551, 295, 807, 167, 679, 423, 935, 103, 615, 359, 871, 231, 743, 487, 999, 23, 535, 279, 791, 151, 663, 407, 919, 87, 599, 343, 855, 215, 727, 471, 983, 55, 567, 311, 823, 183, 695, 439, 951, 119, 631, 375, 887, 247, 759, 503, 1015, 15, 527, 271, 783, 143, 655, 399, 911, 79, 591, 335, 847, 207, 719, 463, 975, 47, 559, 303, 815, 175, 687, 431, 943, 111, 623, 367, 879, 239, 751, 495, 1007, 31, 543, 287, 799, 159, 671, 415, 927, 95, 607, 351, 863, 223, 735, 479, 991, 63, 575, 319, 831, 191, 703, 447, 959, 127, 639, 383, 895, 255, 767, 511, 1023};
    final long[] gauss_1024_12289 = new long[]{1283868770400643928L, 6416574995475331444L, 4078260278032692663L, 2353523259288686585L, 1227179971273316331L, 575931623374121527L, 242543240509105209L, 91437049221049666L, 30799446349977173L, 9255276791179340L, 2478152334826140L, 590642893610164L, 125206034929641L, 23590435911403L, 3948334035941L, 586753615614L, 77391054539L, 9056793210L, 940121950L, 86539696L, 7062824L, 510971L, 32764L, 1862L, 94L, 4L, 0L};
    final int[] MAX_BL_SMALL = new int[]{1, 1, 2, 2, 4, 7, 14, 27, 53, 106, 209};
    final int[] MAX_BL_LARGE = new int[]{2, 2, 5, 7, 12, 21, 40, 78, 157, 308};
    final int[] bitlength_avg = new int[]{4, 11, 24, 50, 102, 202, 401, 794, 1577, 3138, 6308};
    final int[] bitlength_std = new int[]{0, 1, 1, 1, 1, 2, 4, 5, 8, 13, 25};
    final int DEPTH_INT_FG = 4;

    FalconKeyGen() {
        this.fpr = new FPREngine();
        this.primes = new FalconSmallPrimeList();
        this.fft = new FalconFFT();
        this.codec = new FalconCodec();
        this.vrfy = new FalconVrfy();
    }

    private static int mkn(int n) {
        return 1 << n;
    }

    int modp_set(int n, int n2) {
        int n3 = n;
        n3 += n2 & -(n3 >>> 31);
        return n3;
    }

    int modp_norm(int n, int n2) {
        return n - (n2 & (n - (n2 + 1 >>> 1) >>> 31) - 1);
    }

    int modp_ninv31(int n) {
        int n2 = 2 - n;
        n2 *= 2 - n * n2;
        n2 *= 2 - n * n2;
        n2 *= 2 - n * n2;
        n2 *= 2 - n * n2;
        return Integer.MAX_VALUE & -n2;
    }

    int modp_R(int n) {
        return Integer.MIN_VALUE - n;
    }

    int modp_add(int n, int n2, int n3) {
        int n4 = n + n2 - n3;
        n4 += n3 & -(n4 >>> 31);
        return n4;
    }

    int modp_sub(int n, int n2, int n3) {
        int n4 = n - n2;
        n4 += n3 & -(n4 >>> 31);
        return n4;
    }

    int modp_montymul(int n, int n2, int n3, int n4) {
        long l = this.toUnsignedLong(n) * this.toUnsignedLong(n2);
        long l2 = (l * (long)n4 & this.toUnsignedLong(Integer.MAX_VALUE)) * (long)n3;
        int n5 = (int)(l + l2 >>> 31) - n3;
        n5 += n3 & -(n5 >>> 31);
        return n5;
    }

    int modp_R2(int n, int n2) {
        int n3 = this.modp_R(n);
        n3 = this.modp_add(n3, n3, n);
        n3 = this.modp_montymul(n3, n3, n, n2);
        n3 = this.modp_montymul(n3, n3, n, n2);
        n3 = this.modp_montymul(n3, n3, n, n2);
        n3 = this.modp_montymul(n3, n3, n, n2);
        n3 = this.modp_montymul(n3, n3, n, n2);
        n3 = n3 + (n & -(n3 & 1)) >>> 1;
        return n3;
    }

    int modp_Rx(int n, int n2, int n3, int n4) {
        --n;
        int n5 = n4;
        int n6 = this.modp_R(n2);
        int n7 = 0;
        while (1 << n7 <= n) {
            if ((n & 1 << n7) != 0) {
                n6 = this.modp_montymul(n6, n5, n2, n3);
            }
            n5 = this.modp_montymul(n5, n5, n2, n3);
            ++n7;
        }
        return n6;
    }

    int modp_div(int n, int n2, int n3, int n4, int n5) {
        int n6 = n3 - 2;
        int n7 = n5;
        for (int i = 30; i >= 0; --i) {
            n7 = this.modp_montymul(n7, n7, n3, n4);
            int n8 = this.modp_montymul(n7, n2, n3, n4);
            n7 ^= (n7 ^ n8) & -(n6 >>> i & 1);
        }
        n7 = this.modp_montymul(n7, 1, n3, n4);
        return this.modp_montymul(n, n7, n3, n4);
    }

    void modp_mkgm2(int[] nArray, int n, int[] nArray2, int n2, int n3, int n4, int n5, int n6) {
        int n7;
        int n8;
        int n9 = FalconKeyGen.mkn(n3);
        int n10 = this.modp_R2(n5, n6);
        n4 = this.modp_montymul(n4, n10, n5, n6);
        for (n8 = n3; n8 < 10; ++n8) {
            n4 = this.modp_montymul(n4, n4, n5, n6);
        }
        int n11 = this.modp_div(n10, n4, n5, n6, this.modp_R(n5));
        n8 = 10 - n3;
        int n12 = n7 = this.modp_R(n5);
        for (int i = 0; i < n9; ++i) {
            short s2 = this.REV10[i << n8];
            nArray[n + s2] = n12;
            nArray2[n2 + s2] = n7;
            n12 = this.modp_montymul(n12, n4, n5, n6);
            n7 = this.modp_montymul(n7, n11, n5, n6);
        }
    }

    void modp_NTT2_ext(int[] nArray, int n, int n2, int[] nArray2, int n3, int n4, int n5, int n6) {
        int n7;
        if (n4 == 0) {
            return;
        }
        int n8 = n7 = FalconKeyGen.mkn(n4);
        for (int i = 1; i < n7; i <<= 1) {
            int n9 = n8 >> 1;
            int n10 = 0;
            int n11 = 0;
            while (n10 < i) {
                int n12 = nArray2[n3 + i + n10];
                int n13 = n + n11 * n2;
                int n14 = n13 + n9 * n2;
                int n15 = 0;
                while (n15 < n9) {
                    int n16 = nArray[n13];
                    int n17 = this.modp_montymul(nArray[n14], n12, n5, n6);
                    nArray[n13] = this.modp_add(n16, n17, n5);
                    nArray[n14] = this.modp_sub(n16, n17, n5);
                    ++n15;
                    n13 += n2;
                    n14 += n2;
                }
                ++n10;
                n11 += n8;
            }
            n8 = n9;
        }
    }

    void modp_iNTT2_ext(int[] nArray, int n, int n2, int[] nArray2, int n3, int n4, int n5, int n6) {
        if (n4 == 0) {
            return;
        }
        int n7 = FalconKeyGen.mkn(n4);
        int n8 = 1;
        for (int i = n7; i > 1; i >>= 1) {
            int n9 = i >> 1;
            int n10 = n8 << 1;
            int n11 = 0;
            int n12 = 0;
            while (n11 < n9) {
                int n13 = nArray2[n3 + n9 + n11];
                int n14 = n + n12 * n2;
                int n15 = n14 + n8 * n2;
                int n16 = 0;
                while (n16 < n8) {
                    int n17 = nArray[n14];
                    int n18 = nArray[n15];
                    nArray[n14] = this.modp_add(n17, n18, n5);
                    nArray[n15] = this.modp_montymul(this.modp_sub(n17, n18, n5), n13, n5, n6);
                    ++n16;
                    n14 += n2;
                    n15 += n2;
                }
                ++n11;
                n12 += n10;
            }
            n8 = n10;
        }
        int n19 = 1 << 31 - n4;
        int n20 = 0;
        int n21 = n;
        while (n20 < n7) {
            nArray[n21] = this.modp_montymul(nArray[n21], n19, n5, n6);
            ++n20;
            n21 += n2;
        }
    }

    void modp_NTT2(int[] nArray, int n, int[] nArray2, int n2, int n3, int n4, int n5) {
        this.modp_NTT2_ext(nArray, n, 1, nArray2, n2, n3, n4, n5);
    }

    void modp_iNTT2(int[] nArray, int n, int[] nArray2, int n2, int n3, int n4, int n5) {
        this.modp_iNTT2_ext(nArray, n, 1, nArray2, n2, n3, n4, n5);
    }

    void modp_poly_rec_res(int[] nArray, int n, int n2, int n3, int n4, int n5) {
        int n6 = 1 << n2 - 1;
        for (int i = 0; i < n6; ++i) {
            int n7 = nArray[n + (i << 1) + 0];
            int n8 = nArray[n + (i << 1) + 1];
            nArray[n + i] = this.modp_montymul(this.modp_montymul(n7, n8, n3, n4), n5, n3, n4);
        }
    }

    int zint_sub(int[] nArray, int n, int[] nArray2, int n2, int n3, int n4) {
        int n5 = 0;
        int n6 = -n4;
        for (int i = 0; i < n3; ++i) {
            int n7 = nArray[n + i];
            int n8 = n7 - nArray2[n2 + i] - n5;
            n5 = n8 >>> 31;
            n7 ^= (n8 & Integer.MAX_VALUE ^ n7) & n6;
            nArray[n + i] = n7;
        }
        return n5;
    }

    int zint_mul_small(int[] nArray, int n, int n2, int n3) {
        int n4 = 0;
        for (int i = 0; i < n2; ++i) {
            long l = this.toUnsignedLong(nArray[n + i]) * this.toUnsignedLong(n3) + (long)n4;
            nArray[n + i] = (int)l & Integer.MAX_VALUE;
            n4 = (int)(l >> 31);
        }
        return n4;
    }

    int zint_mod_small_unsigned(int[] nArray, int n, int n2, int n3, int n4, int n5) {
        int n6 = 0;
        int n7 = n2;
        while (n7-- > 0) {
            n6 = this.modp_montymul(n6, n5, n3, n4);
            int n8 = nArray[n + n7] - n3;
            n8 += n3 & -(n8 >>> 31);
            n6 = this.modp_add(n6, n8, n3);
        }
        return n6;
    }

    int zint_mod_small_signed(int[] nArray, int n, int n2, int n3, int n4, int n5, int n6) {
        if (n2 == 0) {
            return 0;
        }
        int n7 = this.zint_mod_small_unsigned(nArray, n, n2, n3, n4, n5);
        n7 = this.modp_sub(n7, n6 & -(nArray[n + n2 - 1] >>> 30), n3);
        return n7;
    }

    void zint_add_mul_small(int[] nArray, int n, int[] nArray2, int n2, int n3, int n4) {
        int n5 = 0;
        for (int i = 0; i < n3; ++i) {
            int n6 = nArray[n + i];
            int n7 = nArray2[n2 + i];
            long l = this.toUnsignedLong(n7) * this.toUnsignedLong(n4) + this.toUnsignedLong(n6) + this.toUnsignedLong(n5);
            nArray[n + i] = (int)l & Integer.MAX_VALUE;
            n5 = (int)(l >>> 31);
        }
        nArray[n + n3] = n5;
    }

    void zint_norm_zero(int[] nArray, int n, int[] nArray2, int n2, int n3) {
        int n4 = 0;
        int n5 = 0;
        int n6 = n3;
        while (n6-- > 0) {
            int n7 = nArray[n + n6];
            int n8 = nArray2[n2 + n6] >>> 1 | n5 << 30;
            n5 = nArray2[n2 + n6] & 1;
            int n9 = n8 - n7;
            n9 = -n9 >>> 31 | -(n9 >>> 31);
            n4 |= n9 & (n4 & 1) - 1;
        }
        this.zint_sub(nArray, n, nArray2, n2, n3, n4 >>> 31);
    }

    void zint_rebuild_CRT(int[] nArray, int n, int n2, int n3, int n4, FalconSmallPrime[] falconSmallPrimeArray, int n5, int[] nArray2, int n6) {
        int n7;
        int n8;
        nArray2[n6 + 0] = falconSmallPrimeArray[0].p;
        for (n8 = 1; n8 < n2; ++n8) {
            int n9 = falconSmallPrimeArray[n8].p;
            int n10 = falconSmallPrimeArray[n8].s;
            int n11 = this.modp_ninv31(n9);
            int n12 = this.modp_R2(n9, n11);
            int n13 = 0;
            n7 = n;
            while (n13 < n4) {
                int n14 = nArray[n7 + n8];
                int n15 = this.zint_mod_small_unsigned(nArray, n7, n8, n9, n11, n12);
                int n16 = this.modp_montymul(n10, this.modp_sub(n14, n15, n9), n9, n11);
                this.zint_add_mul_small(nArray, n7, nArray2, n6, n8, n16);
                ++n13;
                n7 += n3;
            }
            nArray2[n6 + n8] = this.zint_mul_small(nArray2, n6, n8, n9);
        }
        if (n5 != 0) {
            n8 = 0;
            n7 = n;
            while (n8 < n4) {
                this.zint_norm_zero(nArray, n7, nArray2, n6, n2);
                ++n8;
                n7 += n3;
            }
        }
    }

    void zint_negate(int[] nArray, int n, int n2, int n3) {
        int n4 = n3;
        int n5 = -n3 >>> 1;
        for (int i = 0; i < n2; ++i) {
            int n6 = nArray[n + i];
            n6 = (n6 ^ n5) + n4;
            nArray[n + i] = n6 & Integer.MAX_VALUE;
            n4 = n6 >>> 31;
        }
    }

    int zint_co_reduce(int[] nArray, int n, int[] nArray2, int n2, int n3, long l, long l2, long l3, long l4) {
        long l5 = 0L;
        long l6 = 0L;
        for (int i = 0; i < n3; ++i) {
            int n4 = nArray[n + i];
            int n5 = nArray2[n2 + i];
            long l7 = (long)n4 * l + (long)n5 * l2 + l5;
            long l8 = (long)n4 * l3 + (long)n5 * l4 + l6;
            if (i > 0) {
                nArray[n + i - 1] = (int)l7 & Integer.MAX_VALUE;
                nArray2[n2 + i - 1] = (int)l8 & Integer.MAX_VALUE;
            }
            l5 = l7 >> 31;
            l6 = l8 >> 31;
        }
        nArray[n + n3 - 1] = (int)l5;
        nArray2[n2 + n3 - 1] = (int)l6;
        int n6 = (int)(l5 >>> 63);
        int n7 = (int)(l6 >>> 63);
        this.zint_negate(nArray, n, n3, n6);
        this.zint_negate(nArray2, n2, n3, n7);
        return n6 | n7 << 1;
    }

    void zint_finish_mod(int[] nArray, int n, int n2, int[] nArray2, int n3, int n4) {
        int n5;
        int n6 = 0;
        for (n5 = 0; n5 < n2; ++n5) {
            n6 = nArray[n + n5] - nArray2[n3 + n5] - n6 >>> 31;
        }
        int n7 = -n4 >>> 1;
        int n8 = -(n4 | 1 - n6);
        n6 = n4;
        for (n5 = 0; n5 < n2; ++n5) {
            int n9 = nArray[n + n5];
            int n10 = (nArray2[n3 + n5] ^ n7) & n8;
            n9 = n9 - n10 - n6;
            nArray[n + n5] = n9 & Integer.MAX_VALUE;
            n6 = n9 >>> 31;
        }
    }

    void zint_co_reduce_mod(int[] nArray, int n, int[] nArray2, int n2, int[] nArray3, int n3, int n4, int n5, long l, long l2, long l3, long l4) {
        long l5 = 0L;
        long l6 = 0L;
        int n6 = (nArray[n + 0] * (int)l + nArray2[n2 + 0] * (int)l2) * n5 & Integer.MAX_VALUE;
        int n7 = (nArray[n + 0] * (int)l3 + nArray2[n2 + 0] * (int)l4) * n5 & Integer.MAX_VALUE;
        for (int i = 0; i < n4; ++i) {
            int n8 = nArray[n + i];
            int n9 = nArray2[n2 + i];
            long l7 = (long)n8 * l + (long)n9 * l2 + (long)nArray3[n3 + i] * this.toUnsignedLong(n6) + l5;
            long l8 = (long)n8 * l3 + (long)n9 * l4 + (long)nArray3[n3 + i] * this.toUnsignedLong(n7) + l6;
            if (i > 0) {
                nArray[n + i - 1] = (int)l7 & Integer.MAX_VALUE;
                nArray2[n2 + i - 1] = (int)l8 & Integer.MAX_VALUE;
            }
            l5 = l7 >> 31;
            l6 = l8 >> 31;
        }
        nArray[n + n4 - 1] = (int)l5;
        nArray2[n2 + n4 - 1] = (int)l6;
        this.zint_finish_mod(nArray, n, n4, nArray3, n3, (int)(l5 >>> 63));
        this.zint_finish_mod(nArray2, n2, n4, nArray3, n3, (int)(l6 >>> 63));
    }

    int zint_bezout(int[] nArray, int n, int[] nArray2, int n2, int[] nArray3, int n3, int[] nArray4, int n4, int n5, int[] nArray5, int n6) {
        int n7;
        int n8;
        if (n5 == 0) {
            return 0;
        }
        int n9 = n;
        int n10 = n2;
        int n11 = n6;
        int n12 = n11 + n5;
        int n13 = n12 + n5;
        int n14 = n13 + n5;
        int n15 = this.modp_ninv31(nArray3[n3 + 0]);
        int n16 = this.modp_ninv31(nArray4[n4 + 0]);
        System.arraycopy(nArray3, n3, nArray5, n13, n5);
        System.arraycopy(nArray4, n4, nArray5, n14, n5);
        nArray[n9 + 0] = 1;
        nArray2[n10 + 0] = 0;
        for (n8 = 1; n8 < n5; ++n8) {
            nArray[n9 + n8] = 0;
            nArray2[n10 + n8] = 0;
        }
        System.arraycopy(nArray4, n4, nArray5, n11, n5);
        System.arraycopy(nArray3, n3, nArray5, n12, n5);
        int n17 = n12 + 0;
        nArray5[n17] = nArray5[n17] - 1;
        for (int i = 62 * n5 + 30; i >= 30; i -= 30) {
            int n18;
            int n19;
            n8 = -1;
            int n20 = -1;
            int n21 = 0;
            int n22 = 0;
            int n23 = 0;
            int n24 = 0;
            n7 = n5;
            while (n7-- > 0) {
                n19 = nArray5[n13 + n7];
                n18 = nArray5[n14 + n7];
                n21 ^= (n21 ^ n19) & n8;
                n22 ^= (n22 ^ n19) & n20;
                n23 ^= (n23 ^ n18) & n8;
                n24 ^= (n24 ^ n18) & n20;
                n20 = n8;
                n8 &= ((n19 | n18) + Integer.MAX_VALUE >>> 31) - 1;
            }
            n22 |= n21 & n20;
            n24 |= n23 & n20;
            long l = (this.toUnsignedLong(n21 &= ~n20) << 31) + this.toUnsignedLong(n22);
            long l2 = (this.toUnsignedLong(n23 &= ~n20) << 31) + this.toUnsignedLong(n24);
            int n25 = nArray5[n13 + 0];
            int n26 = nArray5[n14 + 0];
            long l3 = 1L;
            long l4 = 0L;
            long l5 = 0L;
            long l6 = 1L;
            for (int j = 0; j < 31; ++j) {
                long l7 = l2 - l;
                n19 = (int)((l7 ^ (l ^ l2) & (l ^ l7)) >>> 63);
                n18 = n25 >> j & 1;
                int n27 = n26 >> j & 1;
                int n28 = n18 & n27 & n19;
                int n29 = n18 & n27 & ~n19;
                int n30 = n28 | n18 ^ 1;
                n25 -= n26 & -n28;
                l -= l2 & -this.toUnsignedLong(n28);
                l3 -= l5 & -((long)n28);
                l4 -= l6 & -((long)n28);
                n26 -= n25 & -n29;
                l2 -= l & -this.toUnsignedLong(n29);
                l5 -= l3 & -((long)n29);
                l6 -= l4 & -((long)n29);
                n25 += n25 & n30 - 1;
                l3 += l3 & (long)n30 - 1L;
                l4 += l4 & (long)n30 - 1L;
                l ^= (l ^ l >> 1) & -this.toUnsignedLong(n30);
                n26 += n26 & -n30;
                l5 += l5 & -((long)n30);
                l6 += l6 & -((long)n30);
                l2 ^= (l2 ^ l2 >> 1) & this.toUnsignedLong(n30) - 1L;
            }
            int n31 = this.zint_co_reduce(nArray5, n13, nArray5, n14, n5, l3, l4, l5, l6);
            l3 -= l3 + l3 & -((long)(n31 & 1));
            l4 -= l4 + l4 & -((long)(n31 & 1));
            l5 -= l5 + l5 & -((long)(n31 >>> 1));
            l6 -= l6 + l6 & -((long)(n31 >>> 1));
            this.zint_co_reduce_mod(nArray, n9, nArray5, n11, nArray4, n4, n5, n16, l3, l4, l5, l6);
            this.zint_co_reduce_mod(nArray2, n10, nArray5, n12, nArray3, n3, n5, n15, l3, l4, l5, l6);
        }
        int n32 = nArray5[n13 + 0] ^ 1;
        for (n7 = 1; n7 < n5; ++n7) {
            n32 |= nArray5[n13 + n7];
        }
        return 1 - ((n32 | -n32) >>> 31) & nArray3[n3 + 0] & nArray4[n4 + 0];
    }

    void zint_add_scaled_mul_small(int[] nArray, int n, int n2, int[] nArray2, int n3, int n4, int n5, int n6, int n7) {
        if (n4 == 0) {
            return;
        }
        int n8 = -(nArray2[n3 + n4 - 1] >>> 30) >>> 1;
        int n9 = 0;
        int n10 = 0;
        for (int i = n6; i < n2; ++i) {
            int n11;
            int n12 = i - n6;
            int n13 = n12 < n4 ? nArray2[n3 + n12] : n8;
            int n14 = n13 << n7 & Integer.MAX_VALUE | n9;
            n9 = n13 >>> 31 - n7;
            long l = this.toUnsignedLong(n14) * (long)n5 + this.toUnsignedLong(nArray[n + i]) + (long)n10;
            nArray[n + i] = (int)l & Integer.MAX_VALUE;
            n10 = n11 = (int)(l >>> 31);
        }
    }

    void zint_sub_scaled(int[] nArray, int n, int n2, int[] nArray2, int n3, int n4, int n5, int n6) {
        if (n4 == 0) {
            return;
        }
        int n7 = -(nArray2[n3 + n4 - 1] >>> 30) >>> 1;
        int n8 = 0;
        int n9 = 0;
        for (int i = n5; i < n2; ++i) {
            int n10 = i - n5;
            int n11 = n10 < n4 ? nArray2[n3 + n10] : n7;
            int n12 = n11 << n6 & Integer.MAX_VALUE | n8;
            n8 = n11 >>> 31 - n6;
            int n13 = nArray[n + i] - n12 - n9;
            nArray[n + i] = n13 & Integer.MAX_VALUE;
            n9 = n13 >>> 31;
        }
    }

    int zint_one_to_plain(int[] nArray, int n) {
        int n2 = nArray[n + 0];
        n2 |= (n2 & 0x40000000) << 1;
        return n2;
    }

    void poly_big_to_fp(FalconFPR[] falconFPRArray, int n, int[] nArray, int n2, int n3, int n4, int n5) {
        int n6 = FalconKeyGen.mkn(n5);
        if (n3 == 0) {
            for (int i = 0; i < n6; ++i) {
                falconFPRArray[n + i] = this.fpr.fpr_zero;
            }
            return;
        }
        int n7 = 0;
        while (n7 < n6) {
            int n8 = -(nArray[n2 + n3 - 1] >>> 30);
            int n9 = n8 >>> 1;
            int n10 = n8 & 1;
            FalconFPR falconFPR = this.fpr.fpr_zero;
            FalconFPR falconFPR2 = this.fpr.fpr_one;
            for (int i = 0; i < n3; ++i) {
                int n11 = (nArray[n2 + i] ^ n9) + n10;
                n10 = n11 >>> 31;
                n11 &= Integer.MAX_VALUE;
                n11 -= n11 << 1 & n8;
                falconFPR = this.fpr.fpr_add(falconFPR, this.fpr.fpr_mul(this.fpr.fpr_of(n11), falconFPR2));
                falconFPR2 = this.fpr.fpr_mul(falconFPR2, this.fpr.fpr_ptwo31);
            }
            falconFPRArray[n + n7] = falconFPR;
            ++n7;
            n2 += n4;
        }
    }

    int poly_big_to_small(byte[] byArray, int n, int[] nArray, int n2, int n3, int n4) {
        int n5 = FalconKeyGen.mkn(n4);
        for (int i = 0; i < n5; ++i) {
            int n6 = this.zint_one_to_plain(nArray, n2 + i);
            if (n6 < -n3 || n6 > n3) {
                return 0;
            }
            byArray[n + i] = (byte)n6;
        }
        return 1;
    }

    void poly_sub_scaled(int[] nArray, int n, int n2, int n3, int[] nArray2, int n4, int n5, int n6, int[] nArray3, int n7, int n8, int n9, int n10) {
        int n11 = FalconKeyGen.mkn(n10);
        for (int i = 0; i < n11; ++i) {
            int n12 = -nArray3[n7 + i];
            int n13 = n + i * n3;
            int n14 = n4;
            for (int j = 0; j < n11; ++j) {
                this.zint_add_scaled_mul_small(nArray, n13, n2, nArray2, n14, n5, n12, n8, n9);
                if (i + j == n11 - 1) {
                    n13 = n;
                    n12 = -n12;
                } else {
                    n13 += n3;
                }
                n14 += n6;
            }
        }
    }

    void poly_sub_scaled_ntt(int[] nArray, int n, int n2, int n3, int[] nArray2, int n4, int n5, int n6, int[] nArray3, int n7, int n8, int n9, int n10, int[] nArray4, int n11) {
        int n12;
        int n13;
        int n14;
        int n15 = FalconKeyGen.mkn(n10);
        int n16 = n5 + 1;
        int n17 = n11;
        int n18 = n17 + FalconKeyGen.mkn(n10);
        int n19 = n18 + FalconKeyGen.mkn(n10);
        int n20 = n19 + n15 * n16;
        FalconSmallPrime[] falconSmallPrimeArray = FalconSmallPrimeList.PRIMES;
        for (n14 = 0; n14 < n16; ++n14) {
            int n21;
            int n22 = falconSmallPrimeArray[n14].p;
            int n23 = this.modp_ninv31(n22);
            int n24 = this.modp_R2(n22, n23);
            int n25 = this.modp_Rx(n5, n22, n23, n24);
            this.modp_mkgm2(nArray4, n17, nArray4, n18, n10, falconSmallPrimeArray[n14].g, n22, n23);
            for (n21 = 0; n21 < n15; ++n21) {
                nArray4[n20 + n21] = this.modp_set(nArray3[n7 + n21], n22);
            }
            this.modp_NTT2(nArray4, n20, nArray4, n17, n10, n22, n23);
            n21 = 0;
            n13 = n4;
            n12 = n19 + n14;
            while (n21 < n15) {
                nArray4[n12] = this.zint_mod_small_signed(nArray2, n13, n5, n22, n23, n24, n25);
                ++n21;
                n13 += n6;
                n12 += n16;
            }
            this.modp_NTT2_ext(nArray4, n19 + n14, n16, nArray4, n17, n10, n22, n23);
            n21 = 0;
            n12 = n19 + n14;
            while (n21 < n15) {
                nArray4[n12] = this.modp_montymul(this.modp_montymul(nArray4[n20 + n21], nArray4[n12], n22, n23), n24, n22, n23);
                ++n21;
                n12 += n16;
            }
            this.modp_iNTT2_ext(nArray4, n19 + n14, n16, nArray4, n18, n10, n22, n23);
        }
        this.zint_rebuild_CRT(nArray4, n19, n16, n16, n15, falconSmallPrimeArray, 1, nArray4, n20);
        n14 = 0;
        n12 = n;
        n13 = n19;
        while (n14 < n15) {
            this.zint_sub_scaled(nArray, n12, n2, nArray4, n13, n16, n8, n9);
            ++n14;
            n12 += n3;
            n13 += n16;
        }
    }

    long get_rng_u64(SHAKE256 sHAKE256) {
        byte[] byArray = new byte[8];
        sHAKE256.inner_shake256_extract(byArray, 0, byArray.length);
        return (long)byArray[0] & 0xFFL | ((long)byArray[1] & 0xFFL) << 8 | ((long)byArray[2] & 0xFFL) << 16 | ((long)byArray[3] & 0xFFL) << 24 | ((long)byArray[4] & 0xFFL) << 32 | ((long)byArray[5] & 0xFFL) << 40 | ((long)byArray[6] & 0xFFL) << 48 | ((long)byArray[7] & 0xFFL) << 56;
    }

    int mkgauss(SHAKE256 sHAKE256, int n) {
        int n2 = 1 << 10 - n;
        int n3 = 0;
        for (int i = 0; i < n2; ++i) {
            long l = this.get_rng_u64(sHAKE256);
            int n4 = (int)(l >>> 63);
            int n5 = (int)((l &= Long.MAX_VALUE) - this.gauss_1024_12289[0] >>> 63);
            int n6 = 0;
            l = this.get_rng_u64(sHAKE256);
            l &= Long.MAX_VALUE;
            for (int j = 1; j < this.gauss_1024_12289.length; ++j) {
                int n7 = (int)(l - this.gauss_1024_12289[j] >>> 63) ^ 1;
                n6 |= j & -(n7 & (n5 ^ 1));
                n5 |= n7;
            }
            n6 = (n6 ^ -n4) + n4;
            n3 += n6;
        }
        return n3;
    }

    int poly_small_sqnorm(byte[] byArray, int n, int n2) {
        int n3 = FalconKeyGen.mkn(n2);
        int n4 = 0;
        int n5 = 0;
        for (int i = 0; i < n3; ++i) {
            byte by = byArray[n + i];
            n5 |= (n4 += by * by);
        }
        return n4 | -(n5 >>> 31);
    }

    void poly_small_to_fp(FalconFPR[] falconFPRArray, int n, byte[] byArray, int n2, int n3) {
        int n4 = FalconKeyGen.mkn(n3);
        for (int i = 0; i < n4; ++i) {
            falconFPRArray[n + i] = this.fpr.fpr_of(byArray[n2 + i]);
        }
    }

    void make_fg_step(int[] nArray, int n, int n2, int n3, int n4, int n5) {
        int n6;
        int n7;
        int n8;
        int n9;
        int n10;
        int n11;
        int n12;
        int n13;
        int n14 = 1 << n2;
        int n15 = n14 >> 1;
        int n16 = this.MAX_BL_SMALL[n3];
        int n17 = this.MAX_BL_SMALL[n3 + 1];
        FalconSmallPrime[] falconSmallPrimeArray = FalconSmallPrimeList.PRIMES;
        int n18 = n;
        int n19 = n18 + n15 * n17;
        int n20 = n19 + n15 * n17;
        int n21 = n20 + n14 * n16;
        int n22 = n21 + n14 * n16;
        int n23 = n22 + n14;
        int n24 = n23 + n14;
        System.arraycopy(nArray, n, nArray, n20, 2 * n14 * n16);
        for (n13 = 0; n13 < n16; ++n13) {
            n12 = falconSmallPrimeArray[n13].p;
            n11 = this.modp_ninv31(n12);
            n10 = this.modp_R2(n12, n11);
            this.modp_mkgm2(nArray, n22, nArray, n23, n2, falconSmallPrimeArray[n13].g, n12, n11);
            n9 = 0;
            n8 = n20 + n13;
            while (n9 < n14) {
                nArray[n24 + n9] = nArray[n8];
                ++n9;
                n8 += n16;
            }
            if (n4 == 0) {
                this.modp_NTT2(nArray, n24, nArray, n22, n2, n12, n11);
            }
            n9 = 0;
            n8 = n18 + n13;
            while (n9 < n15) {
                n7 = nArray[n24 + (n9 << 1) + 0];
                n6 = nArray[n24 + (n9 << 1) + 1];
                nArray[n8] = this.modp_montymul(this.modp_montymul(n7, n6, n12, n11), n10, n12, n11);
                ++n9;
                n8 += n17;
            }
            if (n4 != 0) {
                this.modp_iNTT2_ext(nArray, n20 + n13, n16, nArray, n23, n2, n12, n11);
            }
            n9 = 0;
            n8 = n21 + n13;
            while (n9 < n14) {
                nArray[n24 + n9] = nArray[n8];
                ++n9;
                n8 += n16;
            }
            if (n4 == 0) {
                this.modp_NTT2(nArray, n24, nArray, n22, n2, n12, n11);
            }
            n9 = 0;
            n8 = n19 + n13;
            while (n9 < n15) {
                n7 = nArray[n24 + (n9 << 1) + 0];
                n6 = nArray[n24 + (n9 << 1) + 1];
                nArray[n8] = this.modp_montymul(this.modp_montymul(n7, n6, n12, n11), n10, n12, n11);
                ++n9;
                n8 += n17;
            }
            if (n4 != 0) {
                this.modp_iNTT2_ext(nArray, n21 + n13, n16, nArray, n23, n2, n12, n11);
            }
            if (n5 != 0) continue;
            this.modp_iNTT2_ext(nArray, n18 + n13, n17, nArray, n23, n2 - 1, n12, n11);
            this.modp_iNTT2_ext(nArray, n19 + n13, n17, nArray, n23, n2 - 1, n12, n11);
        }
        this.zint_rebuild_CRT(nArray, n20, n16, n16, n14, falconSmallPrimeArray, 1, nArray, n22);
        this.zint_rebuild_CRT(nArray, n21, n16, n16, n14, falconSmallPrimeArray, 1, nArray, n22);
        for (n13 = n16; n13 < n17; ++n13) {
            int n25;
            n12 = falconSmallPrimeArray[n13].p;
            n11 = this.modp_ninv31(n12);
            n10 = this.modp_R2(n12, n11);
            n9 = this.modp_Rx(n16, n12, n11, n10);
            this.modp_mkgm2(nArray, n22, nArray, n23, n2, falconSmallPrimeArray[n13].g, n12, n11);
            n8 = 0;
            n7 = n20;
            while (n8 < n14) {
                nArray[n24 + n8] = this.zint_mod_small_signed(nArray, n7, n16, n12, n11, n10, n9);
                ++n8;
                n7 += n16;
            }
            this.modp_NTT2(nArray, n24, nArray, n22, n2, n12, n11);
            n8 = 0;
            n7 = n18 + n13;
            while (n8 < n15) {
                n6 = nArray[n24 + (n8 << 1) + 0];
                n25 = nArray[n24 + (n8 << 1) + 1];
                nArray[n7] = this.modp_montymul(this.modp_montymul(n6, n25, n12, n11), n10, n12, n11);
                ++n8;
                n7 += n17;
            }
            n8 = 0;
            n7 = n21;
            while (n8 < n14) {
                nArray[n24 + n8] = this.zint_mod_small_signed(nArray, n7, n16, n12, n11, n10, n9);
                ++n8;
                n7 += n16;
            }
            this.modp_NTT2(nArray, n24, nArray, n22, n2, n12, n11);
            n8 = 0;
            n7 = n19 + n13;
            while (n8 < n15) {
                n6 = nArray[n24 + (n8 << 1) + 0];
                n25 = nArray[n24 + (n8 << 1) + 1];
                nArray[n7] = this.modp_montymul(this.modp_montymul(n6, n25, n12, n11), n10, n12, n11);
                ++n8;
                n7 += n17;
            }
            if (n5 != 0) continue;
            this.modp_iNTT2_ext(nArray, n18 + n13, n17, nArray, n23, n2 - 1, n12, n11);
            this.modp_iNTT2_ext(nArray, n19 + n13, n17, nArray, n23, n2 - 1, n12, n11);
        }
    }

    void make_fg(int[] nArray, int n, byte[] byArray, int n2, byte[] byArray2, int n3, int n4, int n5, int n6) {
        int n7 = FalconKeyGen.mkn(n4);
        int n8 = n;
        int n9 = n8 + n7;
        FalconSmallPrime[] falconSmallPrimeArray = FalconSmallPrimeList.PRIMES;
        int n10 = falconSmallPrimeArray[0].p;
        for (int i = 0; i < n7; ++i) {
            nArray[n8 + i] = this.modp_set(byArray[n2 + i], n10);
            nArray[n9 + i] = this.modp_set(byArray2[n3 + i], n10);
        }
        if (n5 == 0 && n6 != 0) {
            int n11 = falconSmallPrimeArray[0].p;
            int n12 = this.modp_ninv31(n11);
            int n13 = n9 + n7;
            int n14 = n13 + n7;
            this.modp_mkgm2(nArray, n13, nArray, n14, n4, falconSmallPrimeArray[0].g, n11, n12);
            this.modp_NTT2(nArray, n8, nArray, n13, n4, n11, n12);
            this.modp_NTT2(nArray, n9, nArray, n13, n4, n11, n12);
            return;
        }
        for (int i = 0; i < n5; ++i) {
            this.make_fg_step(nArray, n, n4 - i, i, i != 0 ? 1 : 0, i + 1 < n5 || n6 != 0 ? 1 : 0);
        }
    }

    int solve_NTRU_deepest(int n, byte[] byArray, int n2, byte[] byArray2, int n3, int[] nArray, int n4) {
        int n5 = this.MAX_BL_SMALL[n];
        FalconSmallPrime[] falconSmallPrimeArray = FalconSmallPrimeList.PRIMES;
        int n6 = n4;
        int n7 = n6 + n5;
        int n8 = n7 + n5;
        int n9 = n8 + n5;
        int n10 = n9 + n5;
        this.make_fg(nArray, n8, byArray, n2, byArray2, n3, n, n, 0);
        this.zint_rebuild_CRT(nArray, n8, n5, n5, 2, falconSmallPrimeArray, 0, nArray, n10);
        if (this.zint_bezout(nArray, n7, nArray, n6, nArray, n8, nArray, n9, n5, nArray, n10) == 0) {
            return 0;
        }
        int n11 = 12289;
        if (this.zint_mul_small(nArray, n6, n5, n11) != 0 || this.zint_mul_small(nArray, n7, n5, n11) != 0) {
            return 0;
        }
        return 1;
    }

    int solve_NTRU_intermediate(int n, byte[] byArray, int n2, byte[] byArray2, int n3, int n4, int[] nArray, int n5) {
        int n6;
        int n7;
        int n8;
        int n9;
        int n10;
        int n11;
        int n12;
        int n13;
        int n14;
        int n15;
        int n16;
        int n17;
        int n18 = n - n4;
        int n19 = 1 << n18;
        int n20 = n19 >> 1;
        int n21 = this.MAX_BL_SMALL[n4];
        int n22 = this.MAX_BL_SMALL[n4 + 1];
        int n23 = this.MAX_BL_LARGE[n4];
        FalconSmallPrime[] falconSmallPrimeArray = FalconSmallPrimeList.PRIMES;
        int n24 = n5;
        int n25 = n24 + n22 * n20;
        int n26 = n25 + n22 * n20;
        this.make_fg(nArray, n26, byArray, n2, byArray2, n3, n, n4, 1);
        int n27 = n5;
        int n28 = n27 + n19 * n23;
        int n29 = n28 + n19 * n23;
        System.arraycopy(nArray, n26, nArray, n29, 2 * n19 * n21);
        n26 = n29;
        int n30 = n26 + n21 * n19;
        n29 = n30 + n21 * n19;
        System.arraycopy(nArray, n24, nArray, n29, 2 * n20 * n22);
        n24 = n29;
        n25 = n24 + n20 * n22;
        for (n17 = 0; n17 < n23; ++n17) {
            n16 = falconSmallPrimeArray[n17].p;
            n15 = this.modp_ninv31(n16);
            n14 = this.modp_R2(n16, n15);
            n13 = this.modp_Rx(n22, n16, n15, n14);
            n12 = 0;
            n11 = n24;
            n10 = n25;
            n9 = n27 + n17;
            n8 = n28 + n17;
            while (n12 < n20) {
                nArray[n9] = this.zint_mod_small_signed(nArray, n11, n22, n16, n15, n14, n13);
                nArray[n8] = this.zint_mod_small_signed(nArray, n10, n22, n16, n15, n14, n13);
                ++n12;
                n11 += n22;
                n10 += n22;
                n9 += n23;
                n8 += n23;
            }
        }
        for (n17 = 0; n17 < n23; ++n17) {
            int n31;
            int n32;
            n16 = falconSmallPrimeArray[n17].p;
            n15 = this.modp_ninv31(n16);
            n14 = this.modp_R2(n16, n15);
            if (n17 == n21) {
                this.zint_rebuild_CRT(nArray, n26, n21, n21, n19, falconSmallPrimeArray, 1, nArray, n29);
                this.zint_rebuild_CRT(nArray, n30, n21, n21, n19, falconSmallPrimeArray, 1, nArray, n29);
            }
            n13 = n29;
            n12 = n13 + n19;
            n11 = n12 + n19;
            n10 = n11 + n19;
            this.modp_mkgm2(nArray, n13, nArray, n12, n18, falconSmallPrimeArray[n17].g, n16, n15);
            if (n17 < n21) {
                n32 = 0;
                n7 = n26 + n17;
                n6 = n30 + n17;
                while (n32 < n19) {
                    nArray[n11 + n32] = nArray[n7];
                    nArray[n10 + n32] = nArray[n6];
                    ++n32;
                    n7 += n21;
                    n6 += n21;
                }
                this.modp_iNTT2_ext(nArray, n26 + n17, n21, nArray, n12, n18, n16, n15);
                this.modp_iNTT2_ext(nArray, n30 + n17, n21, nArray, n12, n18, n16, n15);
            } else {
                n31 = this.modp_Rx(n21, n16, n15, n14);
                n32 = 0;
                n7 = n26;
                n6 = n30;
                while (n32 < n19) {
                    nArray[n11 + n32] = this.zint_mod_small_signed(nArray, n7, n21, n16, n15, n14, n31);
                    nArray[n10 + n32] = this.zint_mod_small_signed(nArray, n6, n21, n16, n15, n14, n31);
                    ++n32;
                    n7 += n21;
                    n6 += n21;
                }
                this.modp_NTT2(nArray, n11, nArray, n13, n18, n16, n15);
                this.modp_NTT2(nArray, n10, nArray, n13, n18, n16, n15);
            }
            n9 = n10 + n19;
            n8 = n9 + n20;
            n32 = 0;
            n7 = n27 + n17;
            n6 = n28 + n17;
            while (n32 < n20) {
                nArray[n9 + n32] = nArray[n7];
                nArray[n8 + n32] = nArray[n6];
                ++n32;
                n7 += n23;
                n6 += n23;
            }
            this.modp_NTT2(nArray, n9, nArray, n13, n18 - 1, n16, n15);
            this.modp_NTT2(nArray, n8, nArray, n13, n18 - 1, n16, n15);
            n32 = 0;
            n7 = n27 + n17;
            n6 = n28 + n17;
            while (n32 < n20) {
                n31 = nArray[n11 + (n32 << 1) + 0];
                int n33 = nArray[n11 + (n32 << 1) + 1];
                int n34 = nArray[n10 + (n32 << 1) + 0];
                int n35 = nArray[n10 + (n32 << 1) + 1];
                int n36 = this.modp_montymul(nArray[n9 + n32], n14, n16, n15);
                int n37 = this.modp_montymul(nArray[n8 + n32], n14, n16, n15);
                nArray[n7 + 0] = this.modp_montymul(n35, n36, n16, n15);
                nArray[n7 + n23] = this.modp_montymul(n34, n36, n16, n15);
                nArray[n6 + 0] = this.modp_montymul(n33, n37, n16, n15);
                nArray[n6 + n23] = this.modp_montymul(n31, n37, n16, n15);
                ++n32;
                n7 += n23 << 1;
                n6 += n23 << 1;
            }
            this.modp_iNTT2_ext(nArray, n27 + n17, n23, nArray, n12, n18, n16, n15);
            this.modp_iNTT2_ext(nArray, n28 + n17, n23, nArray, n12, n18, n16, n15);
        }
        this.zint_rebuild_CRT(nArray, n27, n23, n23, n19, falconSmallPrimeArray, 1, nArray, n29);
        this.zint_rebuild_CRT(nArray, n28, n23, n23, n19, falconSmallPrimeArray, 1, nArray, n29);
        FalconFPR[] falconFPRArray = new FalconFPR[n19];
        FalconFPR[] falconFPRArray2 = new FalconFPR[n19];
        FalconFPR[] falconFPRArray3 = new FalconFPR[n19];
        FalconFPR[] falconFPRArray4 = new FalconFPR[n19];
        FalconFPR[] falconFPRArray5 = new FalconFPR[n19 >> 1];
        int[] nArray2 = new int[n19];
        int n38 = n21 > 10 ? 10 : n21;
        this.poly_big_to_fp(falconFPRArray3, 0, nArray, n26 + n21 - n38, n38, n21, n18);
        this.poly_big_to_fp(falconFPRArray4, 0, nArray, n30 + n21 - n38, n38, n21, n18);
        int n39 = 31 * (n21 - n38);
        int n40 = this.bitlength_avg[n4] - 6 * this.bitlength_std[n4];
        int n41 = this.bitlength_avg[n4] + 6 * this.bitlength_std[n4];
        this.fft.FFT(falconFPRArray3, 0, n18);
        this.fft.FFT(falconFPRArray4, 0, n18);
        this.fft.poly_invnorm2_fft(falconFPRArray5, 0, falconFPRArray3, 0, falconFPRArray4, 0, n18);
        this.fft.poly_adj_fft(falconFPRArray3, 0, n18);
        this.fft.poly_adj_fft(falconFPRArray4, 0, n18);
        int n42 = n23;
        int n43 = 31 * n23;
        int n44 = n43 - n40;
        while (true) {
            FalconFPR falconFPR;
            n38 = n42 > 10 ? 10 : n42;
            n16 = 31 * (n42 - n38);
            this.poly_big_to_fp(falconFPRArray, 0, nArray, n27 + n42 - n38, n38, n23, n18);
            this.poly_big_to_fp(falconFPRArray2, 0, nArray, n28 + n42 - n38, n38, n23, n18);
            this.fft.FFT(falconFPRArray, 0, n18);
            this.fft.FFT(falconFPRArray2, 0, n18);
            this.fft.poly_mul_fft(falconFPRArray, 0, falconFPRArray3, 0, n18);
            this.fft.poly_mul_fft(falconFPRArray2, 0, falconFPRArray4, 0, n18);
            this.fft.poly_add(falconFPRArray2, 0, falconFPRArray, 0, n18);
            this.fft.poly_mul_autoadj_fft(falconFPRArray2, 0, falconFPRArray5, 0, n18);
            this.fft.iFFT(falconFPRArray2, 0, n18);
            n15 = n44 - n16 + n39;
            if (n15 < 0) {
                n15 = -n15;
                falconFPR = this.fpr.fpr_two;
            } else {
                falconFPR = this.fpr.fpr_onehalf;
            }
            FalconFPR falconFPR2 = this.fpr.fpr_one;
            while (n15 != 0) {
                if ((n15 & 1) != 0) {
                    falconFPR2 = this.fpr.fpr_mul(falconFPR2, falconFPR);
                }
                n15 >>= 1;
                falconFPR = this.fpr.fpr_sqr(falconFPR);
            }
            for (n17 = 0; n17 < n19; ++n17) {
                FalconFPR falconFPR3 = this.fpr.fpr_mul(falconFPRArray2[n17], falconFPR2);
                if (!this.fpr.fpr_lt(this.fpr.fpr_mtwo31m1, falconFPR3) || !this.fpr.fpr_lt(falconFPR3, this.fpr.fpr_ptwo31m1)) {
                    return 0;
                }
                nArray2[n17] = (int)this.fpr.fpr_rint(falconFPR3);
            }
            n12 = n44 / 31;
            n13 = n44 % 31;
            if (n4 <= 4) {
                this.poly_sub_scaled_ntt(nArray, n27, n42, n23, nArray, n26, n21, n21, nArray2, 0, n12, n13, n18, nArray, n29);
                this.poly_sub_scaled_ntt(nArray, n28, n42, n23, nArray, n30, n21, n21, nArray2, 0, n12, n13, n18, nArray, n29);
            } else {
                this.poly_sub_scaled(nArray, n27, n42, n23, nArray, n26, n21, n21, nArray2, 0, n12, n13, n18);
                this.poly_sub_scaled(nArray, n28, n42, n23, nArray, n30, n21, n21, nArray2, 0, n12, n13, n18);
            }
            n14 = n44 + n41 + 10;
            if (n14 < n43 && n42 * 31 >= (n43 = n14) + 31) {
                --n42;
            }
            if (n44 <= 0) break;
            if ((n44 -= 25) >= 0) continue;
            n44 = 0;
        }
        if (n42 < n21) {
            n17 = 0;
            while (n17 < n19) {
                n15 = -(nArray[n27 + n42 - 1] >>> 30) >>> 1;
                for (n16 = n42; n16 < n21; ++n16) {
                    nArray[n27 + n16] = n15;
                }
                n15 = -(nArray[n28 + n42 - 1] >>> 30) >>> 1;
                for (n16 = n42; n16 < n21; ++n16) {
                    nArray[n28 + n16] = n15;
                }
                ++n17;
                n27 += n23;
                n28 += n23;
            }
        }
        n17 = 0;
        n7 = n5;
        n6 = n5;
        while (n17 < n19 << 1) {
            System.arraycopy(nArray, n6, nArray, n7, n21);
            ++n17;
            n7 += n21;
            n6 += n23;
        }
        return 1;
    }

    int solve_NTRU_binary_depth1(int n, byte[] byArray, int n2, byte[] byArray2, int n3, int[] nArray, int n4) {
        int n5;
        int n6;
        int n7;
        int n8;
        int n9;
        int n10;
        int n11;
        int n12;
        int n13;
        int n14;
        int n15 = 1;
        int n16 = 1 << n;
        int n17 = n - n15;
        int n18 = 1 << n17;
        int n19 = n18 >> 1;
        int n20 = this.MAX_BL_SMALL[n15];
        int n21 = this.MAX_BL_SMALL[n15 + 1];
        int n22 = this.MAX_BL_LARGE[n15];
        int n23 = n4;
        int n24 = n23 + n21 * n19;
        int n25 = n24 + n21 * n19;
        int n26 = n25 + n22 * n18;
        for (n14 = 0; n14 < n22; ++n14) {
            n13 = FalconSmallPrimeList.PRIMES[n14].p;
            n12 = this.modp_ninv31(n13);
            n11 = this.modp_R2(n13, n12);
            n10 = this.modp_Rx(n21, n13, n12, n11);
            n9 = 0;
            n8 = n23;
            n7 = n24;
            n6 = n25 + n14;
            n5 = n26 + n14;
            while (n9 < n19) {
                nArray[n6] = this.zint_mod_small_signed(nArray, n8, n21, n13, n12, n11, n10);
                nArray[n5] = this.zint_mod_small_signed(nArray, n7, n21, n13, n12, n11, n10);
                ++n9;
                n8 += n21;
                n7 += n21;
                n6 += n22;
                n5 += n22;
            }
        }
        System.arraycopy(nArray, n25, nArray, n4, n22 * n18);
        n25 = n4;
        System.arraycopy(nArray, n26, nArray, n25 + n22 * n18, n22 * n18);
        n26 = n25 + n22 * n18;
        int n27 = n26 + n22 * n18;
        int n28 = n27 + n20 * n18;
        int n29 = n28 + n20 * n18;
        for (n14 = 0; n14 < n22; ++n14) {
            int n30;
            n13 = FalconSmallPrimeList.PRIMES[n14].p;
            n12 = this.modp_ninv31(n13);
            n11 = this.modp_R2(n13, n12);
            n10 = n29;
            n9 = n10 + n16;
            n8 = n9 + n18;
            n7 = n8 + n16;
            this.modp_mkgm2(nArray, n10, nArray, n9, n, FalconSmallPrimeList.PRIMES[n14].g, n13, n12);
            for (n30 = 0; n30 < n16; ++n30) {
                nArray[n8 + n30] = this.modp_set(byArray[n2 + n30], n13);
                nArray[n7 + n30] = this.modp_set(byArray2[n3 + n30], n13);
            }
            this.modp_NTT2(nArray, n8, nArray, n10, n, n13, n12);
            this.modp_NTT2(nArray, n7, nArray, n10, n, n13, n12);
            for (int i = n; i > n17; --i) {
                this.modp_poly_rec_res(nArray, n8, i, n13, n12, n11);
                this.modp_poly_rec_res(nArray, n7, i, n13, n12, n11);
            }
            if (n15 > 0) {
                System.arraycopy(nArray, n9, nArray, n10 + n18, n18);
                n9 = n10 + n18;
                System.arraycopy(nArray, n8, nArray, n9 + n18, n18);
                n8 = n9 + n18;
                System.arraycopy(nArray, n7, nArray, n8 + n18, n18);
                n7 = n8 + n18;
            }
            n6 = n7 + n18;
            n5 = n6 + n19;
            n30 = 0;
            int n31 = n25 + n14;
            int n32 = n26 + n14;
            while (n30 < n19) {
                nArray[n6 + n30] = nArray[n31];
                nArray[n5 + n30] = nArray[n32];
                ++n30;
                n31 += n22;
                n32 += n22;
            }
            this.modp_NTT2(nArray, n6, nArray, n10, n17 - 1, n13, n12);
            this.modp_NTT2(nArray, n5, nArray, n10, n17 - 1, n13, n12);
            n30 = 0;
            n31 = n25 + n14;
            n32 = n26 + n14;
            while (n30 < n19) {
                int n33 = nArray[n8 + (n30 << 1) + 0];
                int n34 = nArray[n8 + (n30 << 1) + 1];
                int n35 = nArray[n7 + (n30 << 1) + 0];
                int n36 = nArray[n7 + (n30 << 1) + 1];
                int n37 = this.modp_montymul(nArray[n6 + n30], n11, n13, n12);
                int n38 = this.modp_montymul(nArray[n5 + n30], n11, n13, n12);
                nArray[n31 + 0] = this.modp_montymul(n36, n37, n13, n12);
                nArray[n31 + n22] = this.modp_montymul(n35, n37, n13, n12);
                nArray[n32 + 0] = this.modp_montymul(n34, n38, n13, n12);
                nArray[n32 + n22] = this.modp_montymul(n33, n38, n13, n12);
                ++n30;
                n31 += n22 << 1;
                n32 += n22 << 1;
            }
            this.modp_iNTT2_ext(nArray, n25 + n14, n22, nArray, n9, n17, n13, n12);
            this.modp_iNTT2_ext(nArray, n26 + n14, n22, nArray, n9, n17, n13, n12);
            if (n14 >= n20) continue;
            this.modp_iNTT2(nArray, n8, nArray, n9, n17, n13, n12);
            this.modp_iNTT2(nArray, n7, nArray, n9, n17, n13, n12);
            n30 = 0;
            n31 = n27 + n14;
            n32 = n28 + n14;
            while (n30 < n18) {
                nArray[n31] = nArray[n8 + n30];
                nArray[n32] = nArray[n7 + n30];
                ++n30;
                n31 += n20;
                n32 += n20;
            }
        }
        this.zint_rebuild_CRT(nArray, n25, n22, n22, n18 << 1, FalconSmallPrimeList.PRIMES, 1, nArray, n29);
        this.zint_rebuild_CRT(nArray, n27, n20, n20, n18 << 1, FalconSmallPrimeList.PRIMES, 1, nArray, n29);
        FalconFPR[] falconFPRArray = new FalconFPR[n18];
        FalconFPR[] falconFPRArray2 = new FalconFPR[n18];
        this.poly_big_to_fp(falconFPRArray, 0, nArray, n25, n22, n22, n17);
        this.poly_big_to_fp(falconFPRArray2, 0, nArray, n26, n22, n22, n17);
        System.arraycopy(nArray, n27, nArray, n4, 2 * n20 * n18);
        n27 = n4;
        n28 = n27 + n20 * n18;
        FalconFPR[] falconFPRArray3 = new FalconFPR[n18];
        FalconFPR[] falconFPRArray4 = new FalconFPR[n18];
        this.poly_big_to_fp(falconFPRArray3, 0, nArray, n27, n20, n20, n17);
        this.poly_big_to_fp(falconFPRArray4, 0, nArray, n28, n20, n20, n17);
        this.fft.FFT(falconFPRArray, 0, n17);
        this.fft.FFT(falconFPRArray2, 0, n17);
        this.fft.FFT(falconFPRArray3, 0, n17);
        this.fft.FFT(falconFPRArray4, 0, n17);
        FalconFPR[] falconFPRArray5 = new FalconFPR[n18];
        FalconFPR[] falconFPRArray6 = new FalconFPR[n18 >> 1];
        this.fft.poly_add_muladj_fft(falconFPRArray5, 0, falconFPRArray, 0, falconFPRArray2, 0, falconFPRArray3, 0, falconFPRArray4, 0, n17);
        this.fft.poly_invnorm2_fft(falconFPRArray6, 0, falconFPRArray3, 0, falconFPRArray4, 0, n17);
        this.fft.poly_mul_autoadj_fft(falconFPRArray5, 0, falconFPRArray6, 0, n17);
        this.fft.iFFT(falconFPRArray5, 0, n17);
        for (n14 = 0; n14 < n18; ++n14) {
            FalconFPR falconFPR = falconFPRArray5[n14];
            if (!this.fpr.fpr_lt(falconFPR, this.fpr.fpr_ptwo63m1) || !this.fpr.fpr_lt(this.fpr.fpr_mtwo63m1, falconFPR)) {
                return 0;
            }
            falconFPRArray5[n14] = this.fpr.fpr_of(this.fpr.fpr_rint(falconFPR));
        }
        this.fft.FFT(falconFPRArray5, 0, n17);
        this.fft.poly_mul_fft(falconFPRArray3, 0, falconFPRArray5, 0, n17);
        this.fft.poly_mul_fft(falconFPRArray4, 0, falconFPRArray5, 0, n17);
        this.fft.poly_sub(falconFPRArray, 0, falconFPRArray3, 0, n17);
        this.fft.poly_sub(falconFPRArray2, 0, falconFPRArray4, 0, n17);
        this.fft.iFFT(falconFPRArray, 0, n17);
        this.fft.iFFT(falconFPRArray2, 0, n17);
        n25 = n4;
        n26 = n25 + n18;
        for (n14 = 0; n14 < n18; ++n14) {
            nArray[n25 + n14] = (int)this.fpr.fpr_rint(falconFPRArray[n14]);
            nArray[n26 + n14] = (int)this.fpr.fpr_rint(falconFPRArray2[n14]);
        }
        return 1;
    }

    int solve_NTRU_binary_depth0(int n, byte[] byArray, int n2, byte[] byArray2, int n3, int[] nArray, int n4) {
        int n5;
        int n6;
        int n7;
        int n8 = 1 << n;
        int n9 = n8 >> 1;
        int n10 = FalconSmallPrimeList.PRIMES[0].p;
        int n11 = this.modp_ninv31(n10);
        int n12 = this.modp_R2(n10, n11);
        int n13 = n4;
        int n14 = n13 + n9;
        int n15 = n14 + n9;
        int n16 = n15 + n8;
        int n17 = n16 + n8;
        int n18 = n17 + n8;
        this.modp_mkgm2(nArray, n17, nArray, n18, n, FalconSmallPrimeList.PRIMES[0].g, n10, n11);
        for (n7 = 0; n7 < n9; ++n7) {
            nArray[n13 + n7] = this.modp_set(this.zint_one_to_plain(nArray, n13 + n7), n10);
            nArray[n14 + n7] = this.modp_set(this.zint_one_to_plain(nArray, n14 + n7), n10);
        }
        this.modp_NTT2(nArray, n13, nArray, n17, n - 1, n10, n11);
        this.modp_NTT2(nArray, n14, nArray, n17, n - 1, n10, n11);
        for (n7 = 0; n7 < n8; ++n7) {
            nArray[n15 + n7] = this.modp_set(byArray[n2 + n7], n10);
            nArray[n16 + n7] = this.modp_set(byArray2[n3 + n7], n10);
        }
        this.modp_NTT2(nArray, n15, nArray, n17, n, n10, n11);
        this.modp_NTT2(nArray, n16, nArray, n17, n, n10, n11);
        for (n7 = 0; n7 < n8; n7 += 2) {
            n6 = nArray[n15 + n7 + 0];
            n5 = nArray[n15 + n7 + 1];
            int n19 = nArray[n16 + n7 + 0];
            int n20 = nArray[n16 + n7 + 1];
            int n21 = this.modp_montymul(nArray[n13 + (n7 >> 1)], n12, n10, n11);
            int n22 = this.modp_montymul(nArray[n14 + (n7 >> 1)], n12, n10, n11);
            nArray[n15 + n7 + 0] = this.modp_montymul(n20, n21, n10, n11);
            nArray[n15 + n7 + 1] = this.modp_montymul(n19, n21, n10, n11);
            nArray[n16 + n7 + 0] = this.modp_montymul(n5, n22, n10, n11);
            nArray[n16 + n7 + 1] = this.modp_montymul(n6, n22, n10, n11);
        }
        this.modp_iNTT2(nArray, n15, nArray, n18, n, n10, n11);
        this.modp_iNTT2(nArray, n16, nArray, n18, n, n10, n11);
        n14 = n13 + n8;
        int n23 = n14 + n8;
        System.arraycopy(nArray, n15, nArray, n13, 2 * n8);
        int n24 = n23 + n8;
        int n25 = n24 + n8;
        int n26 = n25 + n8;
        int n27 = n26 + n8;
        this.modp_mkgm2(nArray, n23, nArray, n24, n, FalconSmallPrimeList.PRIMES[0].g, n10, n11);
        this.modp_NTT2(nArray, n13, nArray, n23, n, n10, n11);
        this.modp_NTT2(nArray, n14, nArray, n23, n, n10, n11);
        int n28 = this.modp_set(byArray[n2 + 0], n10);
        nArray[n27 + 0] = n28;
        nArray[n26 + 0] = n28;
        for (n7 = 1; n7 < n8; ++n7) {
            nArray[n26 + n7] = this.modp_set(byArray[n2 + n7], n10);
            nArray[n27 + n8 - n7] = this.modp_set(-byArray[n2 + n7], n10);
        }
        this.modp_NTT2(nArray, n26, nArray, n23, n, n10, n11);
        this.modp_NTT2(nArray, n27, nArray, n23, n, n10, n11);
        for (n7 = 0; n7 < n8; ++n7) {
            n6 = this.modp_montymul(nArray[n27 + n7], n12, n10, n11);
            nArray[n24 + n7] = this.modp_montymul(n6, nArray[n13 + n7], n10, n11);
            nArray[n25 + n7] = this.modp_montymul(n6, nArray[n26 + n7], n10, n11);
        }
        int n29 = this.modp_set(byArray2[n3 + 0], n10);
        nArray[n27 + 0] = n29;
        nArray[n26 + 0] = n29;
        for (n7 = 1; n7 < n8; ++n7) {
            nArray[n26 + n7] = this.modp_set(byArray2[n3 + n7], n10);
            nArray[n27 + n8 - n7] = this.modp_set(-byArray2[n3 + n7], n10);
        }
        this.modp_NTT2(nArray, n26, nArray, n23, n, n10, n11);
        this.modp_NTT2(nArray, n27, nArray, n23, n, n10, n11);
        for (n7 = 0; n7 < n8; ++n7) {
            n6 = this.modp_montymul(nArray[n27 + n7], n12, n10, n11);
            nArray[n24 + n7] = this.modp_add(nArray[n24 + n7], this.modp_montymul(n6, nArray[n14 + n7], n10, n11), n10);
            nArray[n25 + n7] = this.modp_add(nArray[n25 + n7], this.modp_montymul(n6, nArray[n26 + n7], n10, n11), n10);
        }
        this.modp_mkgm2(nArray, n23, nArray, n26, n, FalconSmallPrimeList.PRIMES[0].g, n10, n11);
        this.modp_iNTT2(nArray, n24, nArray, n26, n, n10, n11);
        this.modp_iNTT2(nArray, n25, nArray, n26, n, n10, n11);
        for (n7 = 0; n7 < n8; ++n7) {
            nArray[n23 + n7] = this.modp_norm(nArray[n24 + n7], n10);
            nArray[n24 + n7] = this.modp_norm(nArray[n25 + n7], n10);
        }
        FalconFPR[] falconFPRArray = new FalconFPR[3 * n8];
        int n30 = 0;
        int n31 = n30 + n8;
        int n32 = n31 + n8;
        for (n7 = 0; n7 < n8; ++n7) {
            falconFPRArray[n32 + n7] = this.fpr.fpr_of(nArray[n24 + n7]);
        }
        this.fft.FFT(falconFPRArray, n32, n);
        System.arraycopy(falconFPRArray, n32, falconFPRArray, n31, n9);
        n32 = n31 + n9;
        for (n7 = 0; n7 < n8; ++n7) {
            falconFPRArray[n32 + n7] = this.fpr.fpr_of(nArray[n23 + n7]);
        }
        this.fft.FFT(falconFPRArray, n32, n);
        this.fft.poly_div_autoadj_fft(falconFPRArray, n32, falconFPRArray, n31, n);
        this.fft.iFFT(falconFPRArray, n32, n);
        for (n7 = 0; n7 < n8; ++n7) {
            nArray[n23 + n7] = this.modp_set((int)this.fpr.fpr_rint(falconFPRArray[n32 + n7]), n10);
        }
        n24 = n23 + n8;
        n25 = n24 + n8;
        n26 = n25 + n8;
        n27 = n26 + n8;
        this.modp_mkgm2(nArray, n24, nArray, n25, n, FalconSmallPrimeList.PRIMES[0].g, n10, n11);
        for (n7 = 0; n7 < n8; ++n7) {
            nArray[n26 + n7] = this.modp_set(byArray[n2 + n7], n10);
            nArray[n27 + n7] = this.modp_set(byArray2[n3 + n7], n10);
        }
        this.modp_NTT2(nArray, n23, nArray, n24, n, n10, n11);
        this.modp_NTT2(nArray, n26, nArray, n24, n, n10, n11);
        this.modp_NTT2(nArray, n27, nArray, n24, n, n10, n11);
        for (n7 = 0; n7 < n8; ++n7) {
            n5 = this.modp_montymul(nArray[n23 + n7], n12, n10, n11);
            nArray[n13 + n7] = this.modp_sub(nArray[n13 + n7], this.modp_montymul(n5, nArray[n26 + n7], n10, n11), n10);
            nArray[n14 + n7] = this.modp_sub(nArray[n14 + n7], this.modp_montymul(n5, nArray[n27 + n7], n10, n11), n10);
        }
        this.modp_iNTT2(nArray, n13, nArray, n25, n, n10, n11);
        this.modp_iNTT2(nArray, n14, nArray, n25, n, n10, n11);
        for (n7 = 0; n7 < n8; ++n7) {
            nArray[n13 + n7] = this.modp_norm(nArray[n13 + n7], n10);
            nArray[n14 + n7] = this.modp_norm(nArray[n14 + n7], n10);
        }
        return 1;
    }

    int solve_NTRU(int n, byte[] byArray, int n2, byte[] byArray2, int n3, byte[] byArray3, int n4, byte[] byArray4, int n5, int n6, int[] nArray, int n7) {
        int n8;
        int n9;
        int n10 = FalconKeyGen.mkn(n);
        if (this.solve_NTRU_deepest(n, byArray3, n4, byArray4, n5, nArray, n7) == 0) {
            return 0;
        }
        if (n <= 2) {
            n9 = n;
            while (n9-- > 0) {
                if (this.solve_NTRU_intermediate(n, byArray3, n4, byArray4, n5, n9, nArray, n7) != 0) continue;
                return 0;
            }
        } else {
            n9 = n;
            while (n9-- > 2) {
                if (this.solve_NTRU_intermediate(n, byArray3, n4, byArray4, n5, n9, nArray, n7) != 0) continue;
                return 0;
            }
            if (this.solve_NTRU_binary_depth1(n, byArray3, n4, byArray4, n5, nArray, n7) == 0) {
                return 0;
            }
            if (this.solve_NTRU_binary_depth0(n, byArray3, n4, byArray4, n5, nArray, n7) == 0) {
                return 0;
            }
        }
        if (byArray2 == null) {
            n3 = 0;
            byArray2 = new byte[n10];
        }
        if (this.poly_big_to_small(byArray, n2, nArray, n7, n6, n) == 0 || this.poly_big_to_small(byArray2, n3, nArray, n7 + n10, n6, n) == 0) {
            return 0;
        }
        int n11 = n7;
        int n12 = n11 + n10;
        int n13 = n12 + n10;
        int n14 = n13 + n10;
        int n15 = n14 + n10;
        FalconSmallPrime[] falconSmallPrimeArray = FalconSmallPrimeList.PRIMES;
        int n16 = falconSmallPrimeArray[0].p;
        int n17 = this.modp_ninv31(n16);
        this.modp_mkgm2(nArray, n15, nArray, n7, n, falconSmallPrimeArray[0].g, n16, n17);
        for (n8 = 0; n8 < n10; ++n8) {
            nArray[n11 + n8] = this.modp_set(byArray2[n3 + n8], n16);
        }
        for (n8 = 0; n8 < n10; ++n8) {
            nArray[n12 + n8] = this.modp_set(byArray3[n4 + n8], n16);
            nArray[n13 + n8] = this.modp_set(byArray4[n5 + n8], n16);
            nArray[n14 + n8] = this.modp_set(byArray[n2 + n8], n16);
        }
        this.modp_NTT2(nArray, n12, nArray, n15, n, n16, n17);
        this.modp_NTT2(nArray, n13, nArray, n15, n, n16, n17);
        this.modp_NTT2(nArray, n14, nArray, n15, n, n16, n17);
        this.modp_NTT2(nArray, n11, nArray, n15, n, n16, n17);
        int n18 = this.modp_montymul(12289, 1, n16, n17);
        for (n8 = 0; n8 < n10; ++n8) {
            n9 = this.modp_sub(this.modp_montymul(nArray[n12 + n8], nArray[n11 + n8], n16, n17), this.modp_montymul(nArray[n13 + n8], nArray[n14 + n8], n16, n17), n16);
            if (n9 == n18) continue;
            return 0;
        }
        return 1;
    }

    void poly_small_mkgauss(SHAKE256 sHAKE256, byte[] byArray, int n, int n2) {
        int n3 = FalconKeyGen.mkn(n2);
        int n4 = 0;
        for (int i = 0; i < n3; ++i) {
            int n5;
            block4: {
                while (true) {
                    if ((n5 = this.mkgauss(sHAKE256, n2)) < -127 || n5 > 127) {
                        continue;
                    }
                    if (i != n3 - 1) break;
                    if ((n4 ^ n5 & 1) == 0) {
                        continue;
                    }
                    break block4;
                    break;
                }
                n4 ^= n5 & 1;
            }
            byArray[n + i] = (byte)n5;
        }
    }

    void keygen(SHAKE256 sHAKE256, byte[] byArray, int n, byte[] byArray2, int n2, byte[] byArray3, int n3, byte[] byArray4, int n4, short[] sArray, int n5, int n6) {
        int n7 = FalconKeyGen.mkn(n6);
        SHAKE256 sHAKE2562 = sHAKE256;
        while (true) {
            int[] nArray;
            int n8;
            int n9;
            int n10;
            int n11;
            int n12;
            int n13;
            FalconFPR[] falconFPRArray = new FalconFPR[3 * n7];
            this.poly_small_mkgauss(sHAKE2562, byArray, n, n6);
            this.poly_small_mkgauss(sHAKE2562, byArray2, n2, n6);
            int n14 = 1 << this.codec.max_fg_bits[n6] - 1;
            for (n13 = 0; n13 < n7; ++n13) {
                if (byArray[n + n13] < n14 && byArray[n + n13] > -n14 && byArray2[n2 + n13] < n14 && byArray2[n2 + n13] > -n14) continue;
                n14 = -1;
                break;
            }
            if (n14 < 0 || ((long)(n12 = (n11 = this.poly_small_sqnorm(byArray, n, n6)) + (n10 = this.poly_small_sqnorm(byArray2, n2, n6)) | -((n11 | n10) >>> 31)) & 0xFFFFFFFFL) >= 16823L) continue;
            int n15 = 0;
            int n16 = n15 + n7;
            int n17 = n16 + n7;
            this.poly_small_to_fp(falconFPRArray, n15, byArray, n, n6);
            this.poly_small_to_fp(falconFPRArray, n16, byArray2, n2, n6);
            this.fft.FFT(falconFPRArray, n15, n6);
            this.fft.FFT(falconFPRArray, n16, n6);
            this.fft.poly_invnorm2_fft(falconFPRArray, n17, falconFPRArray, n15, falconFPRArray, n16, n6);
            this.fft.poly_adj_fft(falconFPRArray, n15, n6);
            this.fft.poly_adj_fft(falconFPRArray, n16, n6);
            this.fft.poly_mulconst(falconFPRArray, n15, this.fpr.fpr_q, n6);
            this.fft.poly_mulconst(falconFPRArray, n16, this.fpr.fpr_q, n6);
            this.fft.poly_mul_autoadj_fft(falconFPRArray, n15, falconFPRArray, n17, n6);
            this.fft.poly_mul_autoadj_fft(falconFPRArray, n16, falconFPRArray, n17, n6);
            this.fft.iFFT(falconFPRArray, n15, n6);
            this.fft.iFFT(falconFPRArray, n16, n6);
            FalconFPR falconFPR = this.fpr.fpr_zero;
            for (n13 = 0; n13 < n7; ++n13) {
                falconFPR = this.fpr.fpr_add(falconFPR, this.fpr.fpr_sqr(falconFPRArray[n15 + n13]));
                falconFPR = this.fpr.fpr_add(falconFPR, this.fpr.fpr_sqr(falconFPRArray[n16 + n13]));
            }
            if (!this.fpr.fpr_lt(falconFPR, this.fpr.fpr_bnorm_max)) continue;
            short[] sArray2 = new short[2 * n7];
            if (sArray == null) {
                n9 = 0;
                sArray = sArray2;
                n8 = n9 + n7;
            } else {
                n9 = n5;
                n8 = 0;
            }
            if (this.vrfy.compute_public(sArray, n9, byArray, n, byArray2, n2, n6, sArray2, n8) != 0 && this.solve_NTRU(n6, byArray3, n3, byArray4, n4, byArray, n, byArray2, n2, n14 = (1 << this.codec.max_FG_bits[n6] - 1) - 1, nArray = n6 > 2 ? new int[28 * n7] : new int[28 * n7 * 3], 0) != 0) break;
        }
    }

    private long toUnsignedLong(int n) {
        return (long)n & 0xFFFFFFFFL;
    }
}

