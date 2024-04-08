(ns invoice-item-test
  (:require [invoice-item :refer :all]
            [clojure.test :refer [deftest is]]))

(deftest discounting
  (is (= (subtotal {:invoice-item/precise-quantity 1
                    :invoice-item/precise-price 10
                    :invoice-item/discount-rate 2})
         9.8)
      "a 'regular discount on one product")
  (is (= (subtotal {:invoice-item/precise-quantity 10
                    :invoice-item/precise-price 10
                    :invoice-item/discount-rate 1})
         99.0)
      "the discount should be considered from the total")
  (is (= (subtotal {:invoice-item/precise-quantity 1
                    :invoice-item/precise-price 10
                    :invoice-item/discount-rate 0})
         10.0)
      "if no discount, should be full price")
  (is (= (subtotal {:invoice-item/precise-quantity 10
                    :invoice-item/precise-price 10
                    :invoice-item/discount-rate 0})
         100.0)
      "no discount, but many products")
  (is (= (subtotal {:invoice-item/precise-quantity 1
                    :invoice-item/precise-price 10
                    :invoice-item/discount-rate 100})
         0.0)
      "if discount is 100%, the total shoudl be zero")
  (is (= (subtotal {:invoice-item/precise-quantity 0
                    :invoice-item/precise-price 10
                    :invoice-item/discount-rate 10})
         0.0)
      "if there is no product the total should be zero")
  (is (= (subtotal {:invoice-item/precise-quantity 10
                    :invoice-item/precise-price 0
                    :invoice-item/discount-rate 10})
         0.0)
      "if price is zero, the total should be zero")
  (is (= (subtotal {:invoice-item/precise-quantity 1
                    :invoice-item/precise-price 10
                    :invoice-item/discount-rate 0.1})
         9.99)
      "if it a decimal in the discount, it is properly considered "))
