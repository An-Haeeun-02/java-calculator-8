package calculator;
import camp.nextstep.edu.missionutils.Console;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Application {

    // 기본 구분자 지정
    private static String DELIMITER = "[,;]";
    //커스텀 구분자 패턴
    private static final Pattern CUSTOM_PATTERN_DELIMITER = Pattern.compile("//(.+)\n(.*)");

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
                throw new IllegalArgumentException();
            }
            try {
                //숫자도 아니고, 구분자도 아니라면 커스텀 문자열 점검 함수 예정

            }catch (IllegalArgumentException e){
                throw new IllegalArgumentException();
            }

        }

        return input;
    }

}
