package com.zirriga.myideademokotlin;

import com.google.api.gax.rpc.ClientStream;
import com.google.api.gax.rpc.ResponseObserver;
import com.google.api.gax.rpc.StreamController;
import com.google.cloud.speech.v1.*;
import com.google.protobuf.ByteString;

import javax.sound.sampled.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MicrophoneRecognition {
    public static String resultVoice = "";
    public static String iskPhrase = "";

    public static void main(String[] args) throws Exception {

        ResponseObserver<StreamingRecognizeResponse> responseObserver = null;
        try (SpeechClient client = SpeechClient.create()) {

            responseObserver =
                    new ResponseObserver<StreamingRecognizeResponse>() {
                        ArrayList<StreamingRecognizeResponse> responses = new ArrayList<>();

                        public void onStart(StreamController controller) {}

                        public void onResponse(StreamingRecognizeResponse response) {
                            responses.add(response);
                        }

                        public void onComplete() {
                            for (StreamingRecognizeResponse response : responses) {
                                StreamingRecognitionResult result = response.getResultsList().get(0);
                                SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
                                iskPhrase = alternative.getTranscript();
                                System.out.printf("Transcript : %s\n", alternative.getTranscript());

                                resultVoice = allOctals(alternative.getTranscript());
                                System.out.printf("Transcript : %s\n", resultVoice);
                            }
                        }

                        public void onError(Throwable t) {
                            System.out.println(t);
                        }
                    };

            ClientStream<StreamingRecognizeRequest> clientStream =
                    client.streamingRecognizeCallable().splitCall(responseObserver);

            RecognitionConfig recognitionConfig =
                    RecognitionConfig.newBuilder()
                            .setEncoding(RecognitionConfig.AudioEncoding.LINEAR16)
                            .setLanguageCode("ru-RU")
                            .setSampleRateHertz(16000)
                            .build();
            StreamingRecognitionConfig streamingRecognitionConfig =
                    StreamingRecognitionConfig.newBuilder().setConfig(recognitionConfig).build();

            StreamingRecognizeRequest request =
                    StreamingRecognizeRequest.newBuilder()
                            .setStreamingConfig(streamingRecognitionConfig)
                            .build(); // The first request in a streaming call has to be a config

            clientStream.send(request);
            // SampleRate:16000Hz, SampleSizeInBits: 16, Number of channels: 1, Signed: true,
            // bigEndian: false
            AudioFormat audioFormat = new AudioFormat(16000, 16, 1, true, false);
            DataLine.Info targetInfo =
                    new DataLine.Info(
                            TargetDataLine.class,
                            audioFormat); // Set the system information to read from the microphone audio stream

            if (!AudioSystem.isLineSupported(targetInfo)) {
                System.out.println("Microphone not supported");
                System.exit(0);
            }
            // Target data line captures the audio stream the microphone produces.
            TargetDataLine targetDataLine = (TargetDataLine) AudioSystem.getLine(targetInfo);
            targetDataLine.open(audioFormat);
            targetDataLine.start();
            System.out.println("Start speaking");
            long startTime = System.currentTimeMillis();
            // Audio Input Stream
            AudioInputStream audio = new AudioInputStream(targetDataLine);
            while (true) {
                long estimatedTime = System.currentTimeMillis() - startTime;
                byte[] data = new byte[6400];
                audio.read(data);
                if (estimatedTime > 5000) { // 60 seconds
                    System.out.println("Stop speaking.");
                    targetDataLine.stop();
                    targetDataLine.close();
                    break;
                }
                request =
                        StreamingRecognizeRequest.newBuilder()
                                .setAudioContent(ByteString.copyFrom(data))
                                .build();
                clientStream.send(request);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        responseObserver.onComplete();
    }

    public static String allOctals(String str) {
        String result = "";
        for (int i = 0; i < str.length(); i = i + 1) {
            int intValue = (int)(str.charAt(i));
            switch (intValue) {
                case (1025):
                    result += "Ё";
                    continue;
                case (1040):
                    result += "А";
                    continue;
                case (1041):
                    result += "Б";
                    continue;
                case (1042):
                    result += "В";
                    continue;
                case (1043):
                    result += "Г";
                    continue;
                case (1044):
                    result += "Д";
                    continue;
                case (1045):
                    result += "Е";
                    continue;
                case (1046):
                    result += "Ж";
                    continue;
                case (1047):
                    result += "3";
                    continue;
                case (1048):
                    result += "И";
                    continue;
                case (1049):
                    result += "Й";
                    continue;
                case (1050):
                    result += "К";
                    continue;
                case (1051):
                    result += "Л";
                    continue;
                case (1052):
                    result += "М";
                    continue;
                case (1053):
                    result += "Н";
                    continue;
                case (1054):
                    result += "О";
                    continue;
                case (1055):
                    result += "П";
                    continue;
                case (1056):
                    result += "Р";
                    continue;
                case (1057):
                    result += "С";
                    continue;
                case (1058):
                    result += "Т";
                    continue;
                case (1059):
                    result += "У";
                    continue;
                case (1060):
                    result += "Ф";
                    continue;
                case (1061):
                    result += "Х";
                    continue;
                case (1062):
                    result += "Ц";
                    continue;
                case (1063):
                    result += "Ч";
                    continue;
                case (1064):
                    result += "Ш";
                    continue;
                case (1065):
                    result += "Щ";
                    continue;
                case (1066):
                    result += "Ъ";
                    continue;
                case (1067):
                    result += "Ы";
                    continue;
                case (1068):
                    result += "Ь";
                    continue;
                case (1069):
                    result += "Э";
                    continue;
                case (1070):
                    result += "Ю";
                    continue;
                case (1071):
                    result += "Я";
                    continue;

                case (1072):
                    result += "а";
                    continue;
                case (1073):
                    result += "б";
                    continue;
                case (1074):
                    result += "в";
                    continue;
                case (1075):
                    result += "г";
                    continue;
                case (1076):
                    result += "д";
                    continue;
                case (1077):
                    result += "е";
                    continue;
                case (1078):
                    result += "ж";
                    continue;
                case (1079):
                    result += "з";
                    continue;
                case (1080):
                    result += "и";
                    continue;
                case (1081):
                    result += "й";
                    continue;
                case (1082):
                    result += "к";
                    continue;
                case (1083):
                    result += "л";
                    continue;
                case (1084):
                    result += "м";
                    continue;
                case (1085):
                    result += "н";
                    continue;
                case (1086):
                    result += "о";
                    continue;
                case (1087):
                    result += "п";
                    continue;
                case (1088):
                    result += "р";
                    continue;
                case (1089):
                    result += "с";
                    continue;
                case (1090):
                    result += "т";
                    continue;
                case (1091):
                    result += "у";
                    continue;
                case (1092):
                    result += "ф";
                    continue;
                case (1093):
                    result += "х";
                    continue;
                case (1094):
                    result += "ц";
                    continue;
                case (1095):
                    result += "ч";
                    continue;
                case (1096):
                    result += "ш";
                    continue;
                case (1097):
                    result += "щ";
                    continue;
                case (1098):
                    result += "ъ";
                    continue;
                case (1099):
                    result += "ы";
                    continue;
                case (1100):
                    result += "ь";
                    continue;
                case (1101):
                    result += "э";
                    continue;
                case (1102):
                    result += "ю";
                    continue;
                case (1103):
                    result += "я";
                    continue;
                case (1105):
                    result += "ё";
                    continue;
            }

        }
        return result;
    }




}
