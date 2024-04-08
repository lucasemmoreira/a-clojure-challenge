(ns clojure-challenge.problem-one-test
  (:require [clojure-challenge.problem-one :refer :all]
            [clojure.test :refer [deftest is]]))

(deftest condition-test
  (is (= true (tax-condition {:taxable/taxes [{:tax/category :iva :tax/rate 19}]})))
  (is (= true (retention-condition {:retentionable/retentions
                                    [{:retention/category :ret_fuente
                                      :retention/rate 1}]}))))
