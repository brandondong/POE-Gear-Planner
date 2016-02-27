package Util;

import Model.AttributeType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Brandon on 2016-02-26.
 *
 * Stores information about a gem
 */
public class GemData {

    private String name;

    private Map<AttributeType, List<Integer>> map;

    /**
     *
     * @param name the name of the gem
     */
    public GemData(String name) {
        this.name = name;
        map = new HashMap<>();
    }

    /**
     *
     * @param type the {@link AttributeType} of interest
     * @param level the level of the gem
     * @return the attribute requirement of a given type at a specified level
     */
    public int getAttributeAtLevel(AttributeType type, int level) {
        if (!map.containsKey(type)) {
            return 0;
        }
        List<Integer> list = map.get(type);
        if (level > list.size()) {
            return list.get(list.size() - 1);
        }
        return list.get(level - 1);
    }

    /**
     *
     * @param type the {@link AttributeType} to be added
     * @param list the requirements of that type
     */
    public void addAttribute(AttributeType type, List<Integer> list) {
        if (!list.isEmpty()) {
            map.put(type, list);
        }
    }

    /**
     *
     * @return the {@link AttributeType} which the gem requires
     */
    public Set<AttributeType> getAttributes() {
        return map.keySet();
    }

    public String getName() {
        return name;
    }
}
