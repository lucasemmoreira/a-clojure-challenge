(ns clojure-challenge.problem-one-test
  (:require [clojure-challenge.problem-one :refer :all]
            [clojure.test :refer [deftest is]]))

(deftest condition-test
  (is (= true (tax-condition {:taxable/taxes [{:tax/category :iva :tax/rate 19}]})))
  (is (= true (retention-condition {:retentionable/retentions
                                    [{:retention/category :ret_fuente
                                      :retention/rate 1}]}))))

;; solution

(def invoice (clojure.edn/read-string (slurp "resources/invoice.edn")))

(deftest problem-one-solution
  (is (= (->> invoice
              :invoice/items
              (filter full-condition))
         '({:invoice-item/id "ii3", :invoice-item/sku "SKU 3",
            :taxable/taxes [#:tax{:id "t3", :category :iva, :rate 19}]}
           {:invoice-item/id "ii4", :invoice-item/sku "SKU 3",
            :retentionable/retentions [#:retention{:id "r2", :category
                                                   :ret_fuente, :rate 1}]}))))
