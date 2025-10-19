package calculator;
import camp.nextstep.edu.missionutils.Console;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Application {

    // 기본 구분자 지정
    private static final String DELIMITER_COMMA = ",";
    private static final String DELIMITER_COLON = ":";
    private static final String DELIMITER = "[,:@]";
    //커스텀 구분자 패턴
    private static final Pattern CUSTOM_PATTERN_DELIMITER = Pattern.compile("//(.+)\n(.*)");
    //커스텀 구분자 지정
    private static String CUSTOM_DELIMITER ="@";

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
            // 지정된 커스텀 문자열 추출, 정제
            String regex = Pattern.quote(matcher.group(1));

            //커스텀 부분을 제외한 뒷부분만 추출
            String modifiedInput = matcher.group(2);

            //커스텀 문자열 @로 일괄 치환
            String replacement = modifiedInput.replaceAll(regex, DELIMITER_COLON);

            //커스텀 부분을 제외한, 가공괸 문자열을 반환함
            return new String(replacement.getBytes());
        } else {
            throw new IllegalArgumentException("커스텀 패턴에 오류가 있습니다.");
        }
    }

    //문자열에 대한 검증 메소드
    private static void preciseCheck(String input) throws IllegalArgumentException {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException();
        }

        //구분자와 숫자 이외의 것 허용 된지 검증 패턴
        String allowedCharsPattern = "[\\d" + DELIMITER.substring(1, DELIMITER.length() - 1) + "]+";

        if (!input.matches("^" + allowedCharsPattern + "$")) {
            throw new IllegalArgumentException("허용 되지 않은 문자가 포함되어 있습니다. " + input);
        }

        //구분자 중복 사용 검증
        String unallowedOverlapPattern = "(" + DELIMITER + "){2,}";

        if (Pattern.compile(unallowedOverlapPattern).matcher(input).find()) {
            throw new IllegalArgumentException("구분자가 중복으로 사용되고 있습니다. "+input);
        }
    }

    //구분자를 기준으로 숫자 분리
    private static  String[] numberExtraction(String input) throws IllegalArgumentException {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException();
        }

        //문자열에 대한 검증 진행
        preciseCheck(input);

        // 구분자를 기준으로 숫자 분리
        return input.split(CUSTOM_DELIMITER);
    }


}
