<?php

namespace app\models;

use Yii;
use yii\base\Model;
use yii\data\ActiveDataProvider;
use app\models\RPAThreads;

/**
 * RPAThreadsSearch represents the model behind the search form about `app\models\RPAThreads`.
 */
class RPAThreadsSearch extends RPAThreads
{
    /**
     * @inheritdoc
     */
    public function rules()
    {
        return [
            [['id', 'pid', 'memory', 'status'], 'integer'],
            [['time', 'CPU', 'command', 'IP'], 'safe'],
        ];
    }

    /**
     * @inheritdoc
     */
    public function scenarios()
    {
        // bypass scenarios() implementation in the parent class
        return Model::scenarios();
    }

    /**
     * Creates data provider instance with search query applied
     *
     * @param array $params
     *
     * @return ActiveDataProvider
     */
    public function search($params)
    {
        $query = RPAThreads::find();

        $dataProvider = new ActiveDataProvider([
            'query' => $query,
        ]);

        $this->load($params);

        if (!$this->validate()) {
            // uncomment the following line if you do not want to return any records when validation fails
            // $query->where('0=1');
            return $dataProvider;
        }

        $query->andFilterWhere([
            'id' => $this->id,
            'pid' => $this->pid,
            'memory' => $this->memory,
            'status' => $this->status,
        ]);

        $query->andFilterWhere(['like', 'time', $this->time])
            ->andFilterWhere(['like', 'CPU', $this->CPU])
            ->andFilterWhere(['like', 'command', $this->command])
            ->andFilterWhere(['like', 'IP', $this->IP]);

        return $dataProvider;
    }
}
