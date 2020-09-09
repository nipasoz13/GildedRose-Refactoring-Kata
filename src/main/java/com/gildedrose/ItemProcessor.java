package com.gildedrose;

import static com.gildedrose.ItemCategory.LEGENDARY;
import static com.gildedrose.ItemCategory.getCategory;

public class ItemProcessor {
    private static final int QUALITY_MAX = 50;
    private static final int QUALITY_MIN = 0;

    private ItemProcessor() {

    }

    public static void processItem(Item item) {
        item.quality = computeItemQuality(item);
        if (getCategory(item) != LEGENDARY) {
            item.sellIn--;
        }
    }

    private static int computeItemQuality(Item item) {
        switch (getCategory(item)) {
            case LEGENDARY:
                return item.quality;
            case AGED_BRIE:
                return computeBrieQuality(item);
            case BACKSTAGE:
                return computeBackstageItemQuality(item);
            case CONJURED:
                return computeConjuredItemQuality(item);
            default:
                return computeOrdinaryItemQuality(item);
        }
    }

    private static int computeBackstageItemQuality(Item item) {
        var appreciationFactor = 1;
        if (item.sellIn <= 5) {
            appreciationFactor = 3;
        } else if (item.sellIn <= 10) {
            appreciationFactor = 2;
        }

        return item.sellIn > 0 ? appreciate(item.quality, appreciationFactor) : 0;
    }

    private static int computeOrdinaryItemQuality(Item item) {
        int depreciation = getOrdinaryItemDepreciation(item);
        return Math.max(item.quality - depreciation, QUALITY_MIN);
    }

    private static int getOrdinaryItemDepreciation(Item item) {
        return item.sellIn > 0 ? 1 : 2;
    }

    private static int computeBrieQuality(Item item) {
        int appreciation = getOrdinaryItemDepreciation(item);
        return appreciate(item.quality, appreciation);
    }

    private static int computeConjuredItemQuality(Item item) {
        var depreciation = getOrdinaryItemDepreciation(item) * 2;
        return depreciate(item, depreciation);
    }

    private static int appreciate(int quality, int appreciationFactor) {
        return Math.min(quality + appreciationFactor, QUALITY_MAX);
    }

    private static int depreciate(Item item, int depreciation) {
        return Math.max(item.quality - depreciation, QUALITY_MIN);
    }
}
