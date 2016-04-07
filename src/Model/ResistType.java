package Model;

import Util.CommonUtil;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by Brandon on 2016-02-19.
 *
 * Represents a resist type
 */
public enum ResistType {
    FIRE, COLD, LIGHTNING, CHAOS;

    public static Collection<ResistType> ELEMENTAL = Arrays.asList(FIRE, COLD, LIGHTNING);

    @Override
    public String toString() {
        return CommonUtil.formatUppercaseString(super.toString());
    }
}
