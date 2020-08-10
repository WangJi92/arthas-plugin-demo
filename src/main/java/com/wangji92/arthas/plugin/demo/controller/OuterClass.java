package com.wangji92.arthas.plugin.demo.controller;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jet
 * @date 10-08-2020
 */
@Slf4j
public class OuterClass {

    private String outerName;

    private int outerAge;

    public class InnerClass {
        private String innerName;
        private int innerAge;

        public void anonymousClassRun(){
            Runnable x = new Runnable() {
                private  String field;
                @Override
                public void run() {
                    log.info(this.getClass().getName());
                }
            };
            x.run();
        }

        public int getInnerAge() {
            return innerAge;
        }

        public class InnerInnerClass {
            private String innerInnerName;
            private int innerInnerAge;

            public int getInnerInnerAge() {
                return innerInnerAge;
            }

            public void setInnerInnerAge(int innerInnerAge) {
                this.innerInnerAge = innerInnerAge;
            }
            public void anonymousInnerInnerClassRun(){
                Runnable x = new Runnable() {
                    @Override
                    public void run() {
                        log.info(this.getClass().getName());
                    }
                };
                x.run();
            }
        }
    }

    public static class StaticInnerClass {
        private static String STATIC_INNER_NAME = "staticInnerName";

        public static String getStaticInnerName() {
            return STATIC_INNER_NAME;
        }

        public void anonymousClassRun(){
            Runnable x = new Runnable() {
                private  String field;
                @Override
                public void run() {
                   log.info(this.getClass().getName());
                }
            };
            x.run();
        }

    }


}
