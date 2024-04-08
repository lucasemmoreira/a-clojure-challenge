(ns clojure-challenge.problem-two
  (:require [clojure.data.json :refer [read-str]]
            [clojure.set]
            [invoice-spec :as spec]))

(def invoice (read-str (slurp "resources/invoice.json") :key-fn keyword))

(defn str->date
  "simple method to turn string to date in a specific format.
  if no format is given it will be used 'dd/MM/yyyy'"
  ([string]
   (str->date string "dd/MM/yyyy"))
  ([string fmt]
   (.parse (java.text.SimpleDateFormat. fmt) string)))

(defn customer-spec
  "standarizing customer map"
  [customer-map]
  {:post [(spec/valid? :invoice/customer %)]}
  (clojure.set/rename-keys customer-map {:company_name :customer/name :email :customer/email}))

(defn taxes-spec
  "standarizing taxes map"
  [taxes-map]
  {:post [(spec/valid? ::spec/tax %)]}
  (-> taxes-map
      (clojure.set/rename-keys {:tax_category :tax/category :tax_rate :tax/rate})
      (update :tax/category (fn [category] (when (= category "IVA") :iva )))
      (update :tax/rate double)))

(defn item-spec
  "standarizing item map"
  [item]
  {:post [(spec/valid? ::spec/invoice-item %)]}
  (-> item
      (select-keys [:price :quantity :sku :taxes])
      (clojure.set/rename-keys {:price :invoice-item/price
                                :quantity :invoice-item/quantity
                                :sku :invoice-item/sku
                                :taxes :invoice-item/taxes})
      (update :invoice-item/taxes (fn [taxes] (mapv taxes-spec taxes)))))

(defn invoice-spec
  "stadarize invoice."
  [{{:keys [issue_date customer items]} :invoice}]
  {:post [(spec/valid? ::spec/invoice %)]}
  {:invoice/issue-date (str->date issue_date)
   :invoice/customer (customer-spec customer)
   :invoice/items (mapv item-spec items)})


(def solution-problem-two
  (-> invoice
      invoice-spec
      (spec/valid?)))
