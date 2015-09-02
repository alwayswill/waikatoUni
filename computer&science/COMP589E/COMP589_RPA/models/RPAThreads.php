<?php

namespace app\models;

use Yii;

/**
 * This is the model class for table "RPA_Threads".
 *
 * @property integer $id
 * @property integer $pid
 * @property integer $memory
 * @property string $startTime
 * @property string $runningTime
 * @property string $CPU
 * @property string $command
 * @property string $stat
 * @property string $IP
 * @property integer $status
 */
class RPAThreads extends \yii\db\ActiveRecord
{
    /**
     * @inheritdoc
     */
    public static function tableName()
    {
        return 'RPA_Threads';
    }

    /**
     * @inheritdoc
     */
    public function rules()
    {
        return [
            [['pid', 'memory', 'startTime', 'runningTime', 'CPU', 'command', 'stat', 'IP'], 'required'],
            [['pid', 'memory', 'status'], 'integer'],
            [['startTime', 'command'], 'string'],
            [['runningTime'], 'string', 'max' => 20],
            [['CPU', 'IP'], 'string', 'max' => 255],
            [['stat'], 'string', 'max' => 10]
        ];
    }

    /**
     * @inheritdoc
     */
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'pid' => 'Pid',
            'memory' => 'Memory',
            'startTime' => 'Start Time',
            'runningTime' => 'Running Time',
            'CPU' => 'Cpu',
            'command' => 'Command',
            'stat' => 'Stat',
            'IP' => 'Ip',
            'status' => 'Status',
        ];
    }
}
