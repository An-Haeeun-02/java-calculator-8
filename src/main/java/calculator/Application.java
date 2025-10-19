package calculator;
import camp.nextstep.edu.missionutils.Console;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Application {

    // 기본 구분자 지정
    private static final String DELIMITER = "[,;]";
    //커스텀 구분자 패턴
    private static final Pattern CUSTOM_PATTERN_DELIMITER = Pattern.compile("//(.+)\n(.*)");
    //커스텀 구분자 지정
    private static String CUSTOM_DELIMITER ="";

    public static void main(String[] args) {
        // TODO: 프로그램 구현
        System.out.println("덧셈할 문자열을 입력해 주세요.");
        String input = Console.readLine();

        try{



        }
        catch (IllegalArgumentException e){
            System.err.println("[에러] " + e.getMessage());
        }

    }


    //1차 문자열 검증 - 앞뒤 문자가 아닌 경우 검사
    private static String defaultCheck(String input) throws IllegalArgumentException {
        //빈 문자열 점검
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException();
        }

        //마지막 문자가 숫자가 아닌 경우 오류 발생
        char lastChar = input.charAt(input.length() - 1);
        if (!Character.isDigit(lastChar)) { //숫자가 아니라면
            throw new IllegalArgumentException();
        }

        //첫번째 문자가 숫자가 아닌경우
        char firstChar = input.charAt(0);
        if (!Character.isDigit(firstChar)) { //숫자가 아니라면
            if (firstChar == ',' || firstChar == ';') { //,나 ; 라면
                throw new IllegalArgumentException("입력이 구분자로 시작합니다.");
            } else {
                //커스텀 문자열 시도로 추청. 점검 코드 시
                input = customCheck(input);
            }
        }
        //입력값 반환
        return input;
    }

    //커스텀 구분자 여부 점검
    private static String customCheck(String input) throws IllegalArgumentException {
        //커스텀 문자열 패턴
        Matcher matcher = CUSTOM_PATTERN_DELIMITER.matcher(input);
        //빈 문자열 점검
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("빈 문자열 입니다.");
        }

        //커스텀 패턴 일지/존재 여부
        if(matcher.matches()){//커스텀 패턴과 일치하면
            // 지정된 커스텀 문자열 추출
            String customDelimiter = matcher.group(1);
            //커스텀 문자열을 저장
            CUSTOM_DELIMITER = customDelimiter;

            //커스텀 부분을 제외한 뒷부분만 추출
            String modifiedInput = matcher.group(2);
            //수정된 문자열로 수정
            input = modifiedInput;
        } else {
            throw new IllegalArgumentException("커스텀 패턴에 오류가 있습니다.");
        }

        //커스텀 부분을 제외한, 가공괸 문자열을 반환함
        return new String(input.getBytes());
    }


}
