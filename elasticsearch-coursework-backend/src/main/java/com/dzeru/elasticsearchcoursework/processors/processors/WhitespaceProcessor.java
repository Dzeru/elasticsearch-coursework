package com.dzeru.elasticsearchcoursework.processors.processors;

import com.dzeru.elasticsearchcoursework.processors.StringProcessor;
import org.springframework.stereotype.Component;

@Component
public class WhitespaceProcessor implements StringProcessor {

    @Override
    public String process(String string) {
        StringBuilder sb = new StringBuilder();
        sb.append(string.charAt(0));

        for(int i = 1; i < string.length(); i++) {
            char iChar = string.charAt(i);
            if(string.charAt(i - 1) != ' ' || iChar != ' ') {
                sb.append(iChar);
            }
        }
        return sb.toString();
    }
}
