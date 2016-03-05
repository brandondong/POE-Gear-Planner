package Model;

import Util.CommonUtil;

/**
 * Created by Brandon on 2016-02-19.
 *
 * Represents a resist type
 */
public enum ResistType {
    FIRE, COLD, LIGHTNING, CHAOS;

    @Override
    public String toString() {
        return CommonUtil.formatUppercaseString(super.toString());
    }
}
