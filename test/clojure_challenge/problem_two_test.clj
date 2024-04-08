(ns clojure-challenge.problem-two-test
  (:require [clojure.data.json :refer [read-str]]
            [clojure-challenge.problem-two :refer :all]
            [invoice-spec :refer :all]
            [clojure.test :refer [deftest is]]))

(deftest key-changing
  (is (= (valid? :invoice/customer (customer-spec {:company_name "a-name"
                                                   :email "a-email"})) true))
  (is (= (valid? :invoice-item/taxes [(taxes-spec {:tax_category "IVA" :tax_rate 0})]) true))
  (is (= (valid? :invoice/items [(item-spec {:price 0.0 :quantity 0.0 :sku "a-sku"
                                             :taxes [{:tax_category "IVA"
                                                      :tax_rate 1.1}]})]) true)))

(def invoice (read-str (slurp "resources/invoice.json") :key-fn keyword))

(deftest solution-problem-two
  (is (= (-> invoice
             invoice-spec
             valid?)
         true)))
