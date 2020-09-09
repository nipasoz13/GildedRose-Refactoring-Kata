package com.gildedrose;

import java.util.Arrays;

import static com.gildedrose.ItemCategory.LEGENDARY;
import static com.gildedrose.ItemCategory.getCategory;

class GildedRose {
    private static final int QUALITY_MAX = 50;
    private static final int QUALITY_MIN = 0;

    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        Arrays.stream(items)
                .forEach((item) -> processItem(item));
    }

    private void processItem(Item item) {
        item.quality = computeItemQuality(item);
        if (getCategory(item) != LEGENDARY) {
            item.sellIn--;
        }
    }

    private int computeItemQuality(Item item) {
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

    private int computeBackstageItemQuality(Item item) {
        var appreciationFactor = 1;
        if (item.sellIn <= 5) {
            appreciationFactor = 3;
        } else if (item.sellIn <= 10) {
            appreciationFactor = 2;
        }

        return item.sellIn > 0 ? appreciate(item.quality, appreciationFactor) : 0;
    }

    private int computeOrdinaryItemQuality(Item item) {
        int depreciation = getOrdinaryItemDepreciation(item);
        return item.quality - depreciation <= QUALITY_MIN ? QUALITY_MIN : item.quality - depreciation;
    }

    private int getOrdinaryItemDepreciation(Item item) {
        return item.sellIn > 0 ? 1 : 2;
    }

    private int computeBrieQuality(Item item) {
        int appreciation = getOrdinaryItemDepreciation(item);
        return appreciate(item.quality, appreciation);
    }

    private int computeConjuredItemQuality(Item item) {
        var depreciation = getOrdinaryItemDepreciation(item) * 2;
        return depreciate(item, depreciation);
    }

    private int appreciate(int quality, int appreciationFactor) {
        return quality + appreciationFactor >= QUALITY_MAX ? QUALITY_MAX : quality + appreciationFactor;
    }

    private int depreciate(Item item, int depreciation) {
        return item.quality - depreciation <= QUALITY_MIN ? QUALITY_MIN : item.quality - depreciation;
    }
}
