(ns clojure-challenge.problem-one)

(defn tax-condition
  "tax condition defined. A specific category and rate. I would
   write here the why."
  [{taxes :taxable/taxes }]
  (some #(and (= (:tax/category %) :iva) (= (:tax/rate %) 19)) taxes))

(defn retention-condition
  "retention condition defined. A specific category and rate. I would
  write there the why."
  [{retention :retentionable/retentions}]
  (some #(and (= (:retention/category %) :ret_fuente) (= (:retention/rate %) 1)) retention))

(defn full-condition
  "the only condition allowable is like an xor condition."
  [invoice]
  (cond
    (and (tax-condition invoice) (retention-condition invoice)) false
    (or (tax-condition invoice) (retention-condition invoice)) true))


