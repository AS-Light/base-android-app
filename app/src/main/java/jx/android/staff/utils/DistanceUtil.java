package jx.android.staff.utils;

import com.amap.api.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2015/11/2.
 */
public class DistanceUtil {

    public LatLng mLatLngStart = null;
    public LatLng mLatLngEnd = null;
    public List<LatLng> mLatLngsProcess = new ArrayList<>();

    Map<Long, List<LatLng>> mLatLngMap = new HashMap<>();
    Map<Long, DistanceMethod> mDistanceMethodMap = new HashMap<>();

    DistanceMethod mDistanceMethodUnit = new DistanceMethod();
    DistanceMethod mDistanceMethodRouted = new DistanceMethod();

    /**
     * 外部接口，唯一结果 - 顺序路径
     */
    public DistanceMethod getDistanceMethodUnit() {
        return mDistanceMethodUnit;
    }

    /**
     * 外部接口，唯一结果 - 规划路径
     */
    public DistanceMethod getDistanceMethodRouted() {
        return mDistanceMethodRouted;
    }

    /**
     * 外部接口，设置起始地点
     */
    public void setLatLngStart(LatLng latLng) {
        mLatLngStart = latLng;
    }

    /**
     * 外部接口，设置结束地点
     */
    public void setmLatLngEnd(LatLng latLng) {
        mLatLngEnd = latLng;
    }

    /**
     * 外部接口，添加过程地点
     */
    public void addLatLngPrecess(LatLng latLng) {
        mLatLngsProcess.add(latLng);
    }

    /**
     * 外部接口，删除过程地点
     */
    public void removeLatLngProcess(int index) {
        mLatLngsProcess.remove(index);
    }

    /**
     * 外部接口，清空过程点
     * */
    public void clearLatLngsProcess() {
        mLatLngsProcess.clear();
    }

    /**
     * 外部接口，开始运算
     */
    public void compute() {

        // 每次清空后重新计算
        mDistanceMethodUnit.mLatLngs.clear();
        mDistanceMethodRouted.mLatLngs.clear();

        // 记录元路径
        if (mLatLngStart != null) {
            mDistanceMethodUnit.mLatLngs.add(mLatLngStart);
        }
        if (!mLatLngsProcess.isEmpty()) {
            mDistanceMethodUnit.mLatLngs.addAll(mLatLngsProcess);
        }
        if (mLatLngEnd != null) {
            mDistanceMethodUnit.mLatLngs.add(mLatLngEnd);
        }

        // 计算所有路径组合
        mLatLngMap = getCombinations(mLatLngStart, mLatLngEnd, mLatLngsProcess);

        if (mLatLngMap.isEmpty()) {
            // 如果没有过程点，计算起点和终点
            mDistanceMethodRouted.mLatLngs.addAll(mDistanceMethodUnit.mLatLngs);
        } else {
            // 如果有过程点
            // 1、记录所有路径和"模糊距离"组合
            mDistanceMethodMap.clear();
            for (long l = 0; l < mLatLngMap.size(); l++) {
                DistanceMethod distanceMethod = new DistanceMethod();
                distanceMethod.mLatLngs = mLatLngMap.get(l);
                distanceMethod.mDistanceFuzzy = getDistanceFuzzy(distanceMethod.mLatLngs);

                mDistanceMethodMap.put(l, distanceMethod);
            }

            // 2、计算出最短距离
            mDistanceMethodRouted = getMinDistanceMethod(mDistanceMethodMap);
        }
    }

    public List<Integer> getRoutedProcessOrder() {
        List<Integer> order = new ArrayList<>();
        for (LatLng latLngOut : mDistanceMethodRouted.mLatLngs) {
            for (int i = 0; i < mLatLngsProcess.size(); i++) {
                LatLng latLngIn = mLatLngsProcess.get(i);
                if (latLngOut.latitude == latLngIn.latitude
                        && latLngOut.longitude == latLngIn.longitude) {
                    order.add(i);
                }
            }
        }

        return order;
    }

    /**
     * 求"模糊距离"
     * 返回:double
     */
    private double getDistanceFuzzy(List<LatLng> latLngs) {
        double distanceFuzzy = 0;
        for (int i = 0; i < latLngs.size() - 1; i++) {
            LatLng start = latLngs.get(i);
            LatLng end = latLngs.get(i + 1);
            distanceFuzzy += Math.pow(start.latitude - end.latitude, 2) + Math.pow(start.longitude - end.longitude, 2);
        }
        return distanceFuzzy;
    }

    /**
     * 获得最短"模糊距离"
     * 返回:DistanceMethod(MIN)
     */
    private DistanceMethod getMinDistanceMethod(Map<Long, DistanceMethod> distanceMethodMap) {
        double distanceFuzzyMin = Double.MAX_VALUE;
        DistanceMethod distanceMethodMin = null;
        for (long l = 0; l < distanceMethodMap.size(); l++) {
            if (distanceFuzzyMin > distanceMethodMap.get(l).mDistanceFuzzy) {
                distanceFuzzyMin = distanceMethodMap.get(l).mDistanceFuzzy;
                distanceMethodMin = distanceMethodMap.get(l);
            }
        }
        return distanceMethodMin;
    }

    /**
     * 求
     */
    public static Map<Long, List<LatLng>> getCombinations(List<LatLng> list) {
        Set<List<LatLng>> results = new LinkedHashSet<>();
        results.add(new ArrayList<LatLng>());
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                LatLng current = list.get(j);
                Set<List<LatLng>> newones = new LinkedHashSet<>();
                for (List<LatLng> latLngs : results) {
                    if (!latLngs.contains(current)) {
                        List<LatLng> listNew = new ArrayList<>();
                        listNew.addAll(latLngs);
                        listNew.add(current);
                        newones.add(listNew);
                    } else {
                        newones.add(latLngs);
                    }
                }
                results.addAll(newones);
            }
        }

        String input = "abc";    //求a/b/c三个字符的全排列
        Set<String> pResult = new LinkedHashSet<String>();
        pResult.add("");
        for (int i = 0; i < input.length(); i++)
            for (int j = 0; j < input.length(); j++) {
                String current = input.substring(j, j + 1);
                Set<String> pnewones = new LinkedHashSet<String>();
                for (String s : pResult) {
                    if (s.indexOf(current) == -1) s += current;
                    pnewones.add(s);
                }
                pResult.addAll(pnewones);
            }

        Map<Long, List<LatLng>> resultMap = new HashMap<>();
        long id = 0;
        for (List<LatLng> latLngs : results) {
            if (latLngs.size() == list.size()) {
                resultMap.put(id++, latLngs);
            }
        }
        return resultMap;
    }

    public static Map<Long, List<LatLng>> getCombinations(LatLng start, LatLng end, List<LatLng> list) {

        Map<Long, List<LatLng>> orderMap = getCombinations(list);
        int mapSize = orderMap.size();
        if (start != null) {
            for (long l = 0; l < mapSize; l++) {
                List<LatLng> orderList = orderMap.get(l);
                orderList.add(0, start);
            }
        }
        if (end != null) {
            for (long l = 0; l < mapSize; l++) {
                List<LatLng> orderList = orderMap.get(l);
                orderList.add(end);

            }
        }
        return orderMap;
    }

    public class DistanceMethod {
        public List<LatLng> mLatLngs = new ArrayList<>();
        public double mDistanceFuzzy;
        public double mDistance;
    }

}
