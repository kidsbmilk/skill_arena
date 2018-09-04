import java.util.HashMap;

public class Longest_Substring_Without_Repeating_Characters {

    public int lengthOfLongestSubstring(String s) {
        if(s.length() == 0) {
            return 0;
        }
        int maxlen = 0;
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
        for(int i = 0, j = 0; i < s.length(); i ++) {
            if(map.containsKey(s.charAt(i))) {
                j = Math.max(j, map.get(s.charAt(i)) + 1);
            }
            map.put(s.charAt(i), i);
            maxlen = Math.max(maxlen, i - j + 1);
        }
        return maxlen;
    }
}
