package com.dzeru.elasticsearchcoursework.processors.processors;

import com.dzeru.elasticsearchcoursework.processors.StringProcessor;
import com.dzeru.elasticsearchcoursework.util.PorterStemmer;
import org.springframework.stereotype.Component;

@Component
public class PorterStemmerProcessor implements StringProcessor {

    @Override
    public String process(String string) {
        String[] words = string.split(" ");
        StringBuilder stringBuilder = new StringBuilder();

        for(String word : words) {
            stringBuilder.append(PorterStemmer.stem(word)).append(" ");
        }

        return stringBuilder.toString();
    }
}
